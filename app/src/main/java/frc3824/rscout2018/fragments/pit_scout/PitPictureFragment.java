package frc3824.rscout2018.fragments.pit_scout;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.flurgle.camerakit.CameraView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import frc3824.rscout2018.R;
import frc3824.rscout2018.database.data_models.TeamPitData;
import id.zelory.compressor.Compressor;
import info.hoang8f.widget.FButton;

/**
 * @class PitPictureFragment
 * @brief Fragment for taking pictures of a team's robot
 */
public class PitPictureFragment extends Fragment implements View.OnClickListener, ImageListener
{
    TeamPitData mTeamPitData;
    ArrayList<String> mPictureFilepaths;
    int mCurrentPosition = -1;

    View mView;
    FButton mTakePicture;
    FButton mSetDefault;
    CameraView mCameraView;
    CarouselView mCarouselView;
    Compressor mCompressor;

    public void setData(TeamPitData teamPitData)
    {
        mTeamPitData = teamPitData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_pit_picture, container, false);

        // Inflate the "Set Default" button
        mSetDefault = mView.findViewById(R.id.set_default);
        mSetDefault.setOnClickListener(this);

        // Inflate the "Take Picture" button
        mTakePicture = mView.findViewById(R.id.take_picture);
        mTakePicture.setOnClickListener(this);

        // Inflate the Camera view
        mCameraView = mView.findViewById(R.id.camera);
        mCameraView.setCameraListener(new CameraListener());
        mCameraView.setVisibility(View.GONE);

        // Inflate the carousel
        mCarouselView = mView.findViewById(R.id.carousel);
        mCarouselView.setImageListener(this);

        // Setup the final destination for the images to be based on the team number (later events with
        // the same team should be able to use earlier images)
        mCompressor = new Compressor(getActivity())
            .setMaxWidth(640)
            .setMaxHeight(480)
            .setCompressFormat(Bitmap.CompressFormat.PNG)
            .setDestinationDirectoryPath(String.format("%s/%d/", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath(), mTeamPitData.getTeamNumber()));

        // If there are pictures then display the default image
        if(mTeamPitData.numberOfPictures() > 0)
        {
            mPictureFilepaths = mTeamPitData.getmPictureFilepaths();
            String defaultFilepath = mTeamPitData.getDefaultPictureFilepath();

            if(!defaultFilepath.isEmpty())
            {
                int index = mPictureFilepaths.indexOf(defaultFilepath);
                // Show the default image
                if(index > -1)
                {
                    mCurrentPosition = index;
                    mCarouselView.setCurrentItem(mCurrentPosition);
                }
                // if the default picture isn't in the list then show the first one
                else
                {
                    mCurrentPosition = 0;
                    mCarouselView.setCurrentItem(mCurrentPosition);
                }
            }
            // if there is no default picture then show the first one
            else
            {
                mCurrentPosition = 0;
                mCarouselView.setCurrentItem(mCurrentPosition);
            }
        }
        // Otherwise hide the carousel
        else
        {
            mCarouselView.setVisibility(View.GONE);
        }

        return mView;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            // Start the camera and  hide the carousel when taking a picture
            case R.id.take_picture:
                mCameraView.start();
                mCameraView.setVisibility(View.VISIBLE);
                mCarouselView.setVisibility(View.GONE);
                mSetDefault.setVisibility(View.GONE);
                break;
            // Set the current image as the default image
            case R.id.set_default:
                mTeamPitData.setDefaultPictureFilepath(mPictureFilepaths.get(mCurrentPosition));
                break;
            // Delete the current image
            case R.id.delete:
                File file = new File(mPictureFilepaths.get(mCurrentPosition));
                if(file.exists())
                {
                    file.delete();
                }
                mPictureFilepaths.remove(mCurrentPosition);

                // Hide the carousel if there are no images
                if(mPictureFilepaths.size() == 0)
                {
                    mCurrentPosition = -1;
                    mCarouselView.setVisibility(View.INVISIBLE);

                }
                // If at the end then go to the beginning
                else if(mCurrentPosition == mPictureFilepaths.size())
                {
                    mCurrentPosition = 0;
                    mCarouselView.setCurrentItem(mCurrentPosition);
                }
                // Load the new image at the same position
                else
                {
                    mCarouselView.setCurrentItem(mCurrentPosition);
                }
                break;
        }
    }

    @Override
    public void setImageForPosition(int position, ImageView imageView)
    {
        if(position > 0 && position < mPictureFilepaths.size())
        {
            Glide.with(this).load(mPictureFilepaths.get(position)).into(imageView);
        }
        else
        {
            // todo(Andrew): error
        }
    }

    private class CameraListener extends com.flurgle.camerakit.CameraListener
    {
        @Override
        public void onPictureTaken(byte[] picture)
        {
            super.onPictureTaken(picture);

            Bitmap result = BitmapFactory.decodeByteArray(picture, 0, picture.length);
            FileOutputStream output = null;
            File file = new File(String.format("%s/%d/", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath(), mTeamPitData.getTeamNumber(), String.format("%d", System.currentTimeMillis())));

            // todo: Replace all the print stack traces with something useful

            try
            {
                output = new FileOutputStream(file);
                result.compress(Bitmap.CompressFormat.PNG, 100, output);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if(output != null)
                    {
                        output.close();
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            try
            {
                file = mCompressor.compressToFile(file);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

            mPictureFilepaths.add(file.getAbsolutePath());
            mCarouselView.setPageCount(mPictureFilepaths.size());
            mCurrentPosition = mPictureFilepaths.size() - 1;
            mCarouselView.setCurrentItem(mCurrentPosition);

            mCameraView.setVisibility(View.GONE);
            mCameraView.stop();
            mCarouselView.setVisibility(View.VISIBLE);
            mSetDefault.setVisibility(View.VISIBLE);
        }
    }
}
