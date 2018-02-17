//Require Vars
//{
//Define things for NodeJS
var app = require('express')();
var http = require('http').Server(app);
var url = require('url');
var mysql = require('mysql');
var winston = require('winston');
var formidable = require('formidable');
var jsdom = require('jsdom');
const { JSDOM } = jsdom;
const { window } = new JSDOM();
const { document } = (new JSDOM('')).window;
global.document = document;

var $ = jQuery = require('jquery')(window);
//}

//Winston Logger Stuff
//{
// set default log level.
var logLevel = 'info'

// Set colors
var customColors = {
  trace: 'white',
  debug: 'green',
  info: 'blue',
  warn: 'yellow',
  crit: 'red',
  fatal: 'red'
}

//Create logger
var logger = new (winston.Logger)({
  colors: customColors,
  level: logLevel,
  levels: {
    fatal: 0,
    crit: 1,
    warn: 2,
    info: 3,
    debug: 4,
    trace: 5
  },
  
  //Set file to write to
  transports: [
    new (winston.transports.Console)({
      colorize: true,
      timestamp: true
    }),
    new (winston.transports.File)({ filename: 'logs.log' })
  ]
})

//Put colors with logger
winston.addColors(customColors)

// Extend logger object to properly log 'Error' types
var origLog = logger.log

// Create logger functions
logger.log = function (level, msg) {
  if (msg instanceof Error) {
    var args = Array.prototype.slice.call(arguments)
    args[1] = msg.stack
    origLog.apply(logger, args)
  } else {
    origLog.apply(logger, arguments)
  }
}

logger.info('Started Server');
 
module.exports = logger
//}

// Connect to DB
var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "rscout",
  database: "rscout"
});

//Sets event key so that we don't send it with every request
var serverEventKey = '2017scmb';

//Sets up JSON parsing for incoming data
var bodyParser = require('body-parser');
app.use(bodyParser.json()); // support json encoded bodies
app.use(bodyParser.urlencoded({ extended: true }));

//Simple server ping. Returns pong
app.get('/ping', function (req, res){
	logger.info('Server pinged');
	res.send('pong');
});

//Sends back an HTML file to add an event to the DB
app.get('/addEvent', function(req, res) {
	var reqEventKey = req.body.eventKey;
	console.log('requesting event ' + reqEventKey);
	var sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='rscout'";
	con.query(sql, function (err, result) {
		var alreadyExists = false;
		if (err) {
			logger.fatal(err);
			throw err;
		};
			for (var i = 0; i < result.length; i++) {
				console.log("Checking Table")
				if (result[i].TABLE_NAME.includes(reqEventKey)) {
					alreadyExists = true;
				}
			}
		if (!alreadyExists) {
			addEvent(reqEventKey);
			$.ajax({
				url: "https://www.thebluealliance.com/api/v3/event/" + reqEventKey + "/teams/simple",
				type: 'GET',
				dataType: 'json',
				headers: {
					'X-TBA-Auth-Key': 'jFZAiivEncdZC24mwCGqWnImGrGJdwVRBP9m0djqwY25I42B1NpocGJikWZSu0CZ'
				},
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
				success: function (result) {
					for(var i = 0; i < result.length; i++) {
						addTeam(result[i], reqEventKey);
					}
				},
				error: function (error) {
					console.log(error);
					logger.crit(error);
				}
			});
			$.ajax({
				url: "https://www.thebluealliance.com/api/v3/event/" + reqEventKey + "/matches/simple",
				type: 'GET',
				dataType: 'json',
				headers: {
					'X-TBA-Auth-Key': 'jFZAiivEncdZC24mwCGqWnImGrGJdwVRBP9m0djqwY25I42B1NpocGJikWZSu0CZ'
				},
			contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
			success: function (result) {
				for(var i = 0; i < result.length; i++) {;
						if (!result[i].key.substr(reqEventKey.length + 1).includes('f')) {
							addMatch(result[i], reqEventKey);
						}
					}
				},
				error: function (error) {
					console.log(error);
					logger.crit(error);
				}
			});
		} else {
			console.log("Error! Event already added!");
			logger.crit('Error! Tried to add event which ')
		}
	});
});

//Resets the server's event key
app.post('/setServerEventKey', function(req, res){
	serverEventKey = req.body.key;
	logger.info('Reset server event key');
});

//Pulls out everything in the database and returns it
app.get('/getFullDB', function (req, res) {
	logger.info('Recieved getFullDB request');
	
	//Create blank object
	var fullDB = {matchData:'', matchSchedule:'', pitData:'', teamStats:'', supermatchdata:''};
	
	//SQL statements are nested to ensure they all happen in order
	
	//Pull match data table
	var sql = "SELECT * FROM " + serverEventKey + "matchdata";
	con.query(sql, function (err, result) {
		
		if (err) {
			logger.fatal(err);
			throw err;
		};
		
		//JSON encode and add to object
		JSONresult = JSON.stringify(result);
		fullDB.matchData = JSONresult;
		
		//Pull match schedule table
		var sql = "SELECT * FROM " + serverEventKey + "matchschedule";
		con.query(sql, function (err, result) {
			
			if (err) {
				logger.fatal(err);
				throw err;
			
			};
			
			//JSON encode and add to object
			JSONresult = JSON.stringify(result);
			fullDB.matchSchedule = JSONresult;
			
			//Pull pit data table
			var sql = "SELECT * FROM " + serverEventKey + "pitdata";
			con.query(sql, function (err, result) {
				
				if (err) {
					logger.fatal(err);
					throw err;
				};
				
				//JSON encode result and add to object
				JSONresult = JSON.stringify(result);
				fullDB.pitdata = JSONresult;
				
				//Pull team stats table
				var sql = "SELECT * FROM " + serverEventKey + "teamstats";
				con.query(sql, function (err, result) {
					
					if (err) {
						logger.fatal(err);
						throw err;
					};
					
					//JSON encode and add to object
					JSONresult = JSON.stringify(result);
					fullDB.teamstats = JSONresult;
					
					//Pull super match data table
					var sql = "SELECT * FROM " + serverEventKey + "supermatchdata";
					con.query(sql, function (err, result) {
						
						if (err) {
							logger.fatal(err);
							throw err;
						};
						
						//JSON encode and add to object
						JSONresult = JSON.stringify(result);
						fullDB.supermatchdata = JSONresult;
						
						//Send completed object
						res.send(fullDB);
						logger.info('Sent full database');
					});
				});
			});
		});
	});
});

//Pulls out match schedule information about a specified match
app.get('/getMatchSchedInfo', function (req, res) {
	logger.info('Recieved getMatchSchedInfo request');
	var sql = "SELECT * FROM " + serverEventKey + "matchschedule WHERE matchKey='" + req.body.matchKey + "'";
	con.query(sql, function (err, result) {
		var alreadyExists = false;
		if (err) {
			logger.fatal(err);
			throw err;
		};
		JSONresult = JSON.stringify(result[0]);
		console.log(JSONresult);
		res.send(JSONresult);
		logger.info('Sent match sched info');
	});
});

//Pulls out match data for a specified match and team
app.get('/getMatchData', function (req, res) {
	logger.info('Recieved get match data request');
	//Pulls out match data
	var sql = "SELECT * FROM " + serverEventKey + "matchdata WHERE matchKey='" + req.body.matchKey + "' AND teamNumber='" + req.body.teamNumber + "'";
	con.query(sql, function (err, result) {
		var alreadyExists = false;
		if (err) {
			logger.fatal(err);
			throw err;
		};
		JSONresult = JSON.stringify(result[0]);
		console.log(JSONresult);
		res.send(JSONresult);
		logger.info('Sent match data');
	});
});

//Inserts match data to the DB and factors it into the averages
app.post('/insertMatchData', function (req, res) {
	logger.info('Recieved insertMatchData');
	requestData = req.body;
	console.log('InsertingMatchData');
	console.log(requestData);
	addMatchData(res, serverEventKey, requestData.teamNumber, requestData.matchKey, requestData.autoCrossed, requestData.autoEvents, requestData.teleopEvents, requestData.climbingState, requestData.climbingMethod, requestData.fouls, requestData.techFouls, requestData.yellowCard, requestData.redCard, requestData.notes);
});

//Pulls out pit data for a specified team
app.get('/getPitData', function (req, res) {
	logger.info('Recieved getPitData request');
	//Pulls out pit data
	var sql = "SELECT * FROM " + serverEventKey + "pitdata WHERE teamNumber='" + req.body.teamNumber + "'";
	con.query(sql, function (err, result) {
		var alreadyExists = false;
		if (err) {
			logger.fatal(err);
			throw err;
		};
		JSONresult = JSON.stringify(result[0]);
		console.log(JSONresult);
		res.send(JSONresult);
		logger.info('Sent pit data');
	});
});

//Inserts pit data to the DB
app.get('/insertPitData', function (req, res) {
	logger.info('Recieved insertPitData');
	//Puts in pit data
	requestData = JSON.parse(request.data);
	console.log('InsertingPitData');
	console.log(requestData);
	addPitData(res, request.eventKey, requestData.teamNumber, requestData.width, requestData.length, requestData.height, requestData.weight, requestData.driveTrain, requestData.programmingLanguage, requestData.cims, requestData.notes);
});

//Filter strings to avoid SQL injection
function mysql_real_escape_string (str) {
   return str.replace(/[\0\x08\x09\x1a\n\r"'\\\%]/g, function (char) {
		   switch (char) {
		   case "\0":
			   return "\\0";
		   case "\x08":
			   return "\\b";
			case "\x09":
			   return "\\t";
		   case "\x1a":
			   return "\\z";
		   case "\n":
			   return "\\n";
		   case "\r":
			   return "\\r";
		   case "\"":
			   case "'":
		   case "\\":
		   case "%":
			   return "\\"+char; // prepends a backslash to backslash, percent,
								 // and double/single quotes
	   };
   });
};

//Insert Match Data
function addMatchData(res, eventKey, teamNumber, matchKey, autoCrossed, autoEvents, teleopEvents, climbingState, climbingMethod, fouls, techFouls, yellowCard, redCard, notes) {
	
	//Check if the event has been created
	var alreadyExists = false;
	
	//Pull names of all tables in DB
	var sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='rscout'";
	con.query(sql, function (err, result) {
		
		if (err) {
			logger.fatal(err);
			throw err;
		};
		
		//Run through the results and determine if the event has been added to the DB
		for (var i = 0; i < result.length; i++) {
			console.log("Checking Table")
			if (result[i].TABLE_NAME.includes(eventKey)) {
				alreadyExists = true;
			}
		}
		
		//If event has not been created, log to console. Otherwise, add stuff to DB
		if (!alreadyExists) {
			logger.crit("Error! Event has not been created");
			console.log("Error! Event has not been created");
		} else {
			
			//Insert match data
			//{
			var sql = "INSERT INTO " + eventKey + "matchdata (teamNumber, matchKey, autoEvents, teleopEvents, climbingState, climbingMethod, fouls, techFouls, yellowCard, redCard, notes) VALUES ('" + teamNumber + "', '" + matchKey + "', '" + autoCrossed + "','" + autoEvents + "','" + teleopEvents + "','" + climbingState + "','" + climbingMethod + "','" + fouls + "','" + techFouls + "','" + yellowCard + "','" + redCard + "','" + mysql_real_escape_string(notes) + "')";
			con.query(sql, function (err, result) {
				if (err) {
					logger.fatal(err);
					throw err;
				};
				console.log("1 match data record inserted");
			});
			//}
			
			//Update total and average stats
			//{
			var sql = "SELECT * FROM " + eventKey + "teamstats WHERE teamNumber = '" + teamNumber + "'";
			con.query(sql, function (err, result1) {
				if (err) throw err;
				
				var newNumMatches = result1[0].matchesPlayed + 1;
				
				if (newNumMatches < 1) {
					logger.crit("Tried to divide by zero when updating averages");
				} else {	
			
				if (yellowCard) {
					var yellowCards = 1;
				} else {
					var yellowCards = 0;
				}
				
				if (redCard) {
					var redCards = 1;
				} else {
					var redCards = 0;
				}
				
				var newAutoCrossedTotal = (result1[0].autoCrossedTotal + autoCrossed);
				var newFoulTotal = (result1[0].foulTotal + fouls);
				var newTechFoulTotal = (result1[0].techFoulTotal + techFouls);
				var newYellowCardTotal = (result1[0].yellowCardTotal + yellowCards);
				var newRedCardTotal = (result1[0].redCardTotal + redCards);
				
				var climbStateObject = JSON.parse(result1[0].climbingStateTotals);
				
				console.log(climbingState);
				switch (climbingState) {
					case "No climb attempt":
						climbStateObject.noAttempt++;
						break;
					case "Parked on platform":
						climbStateObject.parkedOnPlatform++;
						break;
					case "Did not finish in time":
						climbStateObject.didNotFinishInTime++;
						break;
					case "Robot fell":
						climbStateObject.robotFell++;
						break;
					case "Successful":
						climbStateObject.successful++;
						break;
					default:
						break;
				}
				
				var climbMethodObject = JSON.parse(result1[0].climbingMethodTotals);
				switch (climbingMethod) {
					case "Climbed on rung, not supporting another robot":
						climbMethodObject.climbOnRung++;
						break;
					case "Climbed on rung, supporting another robot":
						climbMethodObject.climbOnRungWithOne++;
						break;
					case "Climbed on rung, supporting 2 other robots":
						climbMethodObject.climbOnRungWithTwo++;
						break;
					case "Climbed on a rung on another robot":
						climbMethodObject.climbOnRungOfOtherBot++;
						break;
					case "Climbed on platform of another robot":
						climbMethodObject.climbOnPlatformOfOtherBot++;
						break;
					case "Supported another robot on platform":
						climbMethodObject.supportOneOnPlatform++;
						break;
					case "Supported 2 other robots on platform":
						climbMethodObject.supportTwoOnPlatform++;
						break;
					case "Credited through foul":
						climbMethodObject.creditThroughFoul++;
						break;
					case "Credited through levitate, but not supporting other robots":
						climbMethodObject.creditThroughLevitate++;
						break;
					default:
						break;
				}
				
				var autoEventsArray = JSON.parse(autoEvents);
				var teleopEventsArray = JSON.parse(teleopEvents);
				
				var autoEventTotalObject = JSON.parse(result1[0].autoCubeTotals);
				var teleopEventTotalObject = JSON.parse(result1[0].autoCubeTotals);
				
				for (var i = 0; i < autoEventsArray; i++) {
					switch (autoEventsArray[i].name) {
						case "Placed":
							autoEventTotalObject.placed++;
							break;
						case "Dropped":
							autoEventTotalObject.dropped++;
							break;
						case "Launch Success":
							autoEventTotalObject.launchSuccess++;
							break;
						case "Launch Failure":
							autoEventTotalObject.launchFailure++;
							break;
						case "Picked Up":
							autoEventTotalObject.pickedUp++;
							break;
						default:
							logger.crit("Unknown Event Entered");
							break;
					}
				}
				
				for (var i = 0; i < teleopEventsArray; i++) {
					switch (teleopEventsArray[i].name) {
						case "Placed":
							teleopEventTotalObject.placed++;
							break;
						case "Dropped":
							teleopEventTotalObject.dropped++;
							break;
						case "Launch Success":
							teleopEventTotalObject.launchSuccess++;
							break;
						case "Launch Failure":
							teleopEventTotalObject.launchFailure++;
							break;
						case "Picked Up":
							teleopEventTotalObject.pickedUp++;
							break;
						default:
							logger.crit("Unknown Event Entered");
							break;
					}
				}
				
				var autoEventAverageObject = JSON.parse(result1[0].autoCubeAverages);
				var teleopEventAverageObject = JSON.parse(result1[0].teleopCubeAverages);
				
				autoEventAverageObject.placed = autoEventTotalObject.placed / newNumMatches;
				autoEventAverageObject.dropped = autoEventTotalObject.dropped / newNumMatches;
				autoEventAverageObject.launchSuccess = autoEventTotalObject.launchSuccess / newNumMatches;
				autoEventAverageObject.launchFailure = autoEventTotalObject.launchFailure / newNumMatches;
				autoEventAverageObject.pickedUp = autoEventTotalObject.pickedUp / newNumMatches;
				
				teleopEventAverageObject.placed = teleopEventTotalObject.placed / newNumMatches;
				teleopEventAverageObject.dropped = teleopEventTotalObject.dropped / newNumMatches;
				teleopEventAverageObject.launchSuccess = teleopEventTotalObject.launchSuccess / newNumMatches;
				teleopEventAverageObject.launchFailure = teleopEventTotalObject.launchFailure / newNumMatches;
				teleopEventAverageObject.pickedUp = teleopEventTotalObject.pickedUp / newNumMatches;
				
				// Update with new stats
				var sql = "UPDATE " + eventKey + "teamstats SET matchesPlayed = '" + newNumMatches + "', autoCrossedTotal = '" + newAutoCrossedTotal + "', autoCrossedAverage = '" + (newAutoCrossedTotal/newNumMatches) + "', autoEventTotals = '" + autoEventTotalObject + "', autoEventAverages = '" + autoEventAverageObject + "', teleopEventTotals = '" + teleopEventTotalObject + "', climbingStateTotals = '" + JSON.stringify(climbStateObject) + "', climbingMethodTotals = '" + JSON.stringify(climbMethodObject) + "', foulTotal = '" + newFoulTotal + "', foulAverage = '" + ( newFoulTotal/newNumMatches) + "', techFoulTotal = '" + newTechFoulTotal + "', techFoulAverage = '" + (newTechFoulTotal/newNumMatches) + "', yellowCardTotal = '" + newYellowCardTotal + "', yellowCardAverage = '" + ( newYellowCardTotal/newNumMatches) + "', redCardTotal = '" + newRedCardTotal + "', redCardAverage = '" + ( newRedCardTotal/newNumMatches) + "' WHERE teamNumber = '" + teamNumber + "';";
				con.query(sql, function (err, result) {
					if (err) throw err;
					console.log("1 team record updated");
					res.send("1 match added");
				});
				
				}
			});
			//}
		}
	});
};

//Insert Super Match Data
function addSuperMatchData(eventKey, matchKey, boostCubes, forceCubes, levitateCubes, notes) {
	var alreadyExists = false;
	var sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='rscout'";
	con.query(sql, function (err, result) {
		if (err) {
			logger.fatal(err);
			throw err;
		};
		for (var i = 0; i < result.length; i++) {
			console.log("Checking Table")
			if (result[i].TABLE_NAME.includes(eventKey)) {
				alreadyExists = true;
			}
		}	
		if (!alreadyExists) {
		console.log("Error! Event has not been created");
		} else {
			var sql = "INSERT INTO " + eventKey + "supermatchdata (matchKey, boostCubes, forceCubes, levitateCubes, notes) VALUES ('" + matchKey + "', '" + boostCubes + "', '" + forceCubes + "', '" + levitateCubes + "', '" + mysql_real_escape_string(notes) + "')";
			con.query(sql, function (err, result) {
				if (err) {
					logger.fatal(err);
					throw err;
				};
				console.log("1 super match data record inserted");
			});
		}
	});
};

//Insert Pit Data
function addPitData(eventKey, teamNumber, width, length, height, weight, driveTrain, programmingLanguage, cims, notes) {
	var alreadyExists = false;
	var sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='rscout'";
	con.query(sql, function (err, result) {
		if (err) {
			logger.fatal(err);
			throw err;
		};
		for (var i = 0; i < result.length; i++) {
			console.log("Checking Table")
			if (result[i].TABLE_NAME.includes(eventKey)) {
				alreadyExists = true;
			}
		}	
		if (!alreadyExists) {
		console.log("Error! Event has not been created");
		} else {
			var sql = "INSERT INTO " + eventKey + "pitdata (teamNumber, width, length, height, weight, driveTrain, programmingLanguage, cims, notes) VALUES ('" + teamNumber + "', '" + width + "', '" + length + "','" + height + "','" + weight + "','" + driveTrain + "','" + programmingLanguage + "','" + cims + "','" + mysql_real_escape_string(notes) + "')";
			con.query(sql, function (err, result) {
				if (err) {
					logger.fatal(err);
					throw err;
				};
				console.log("1 pit data record inserted");
				res.send("1 pit data record inserted");
			});
		}
	});
};

//Listen on port 3824
var server = http.listen(3824, function(err){
	if (err) {
		logger.fatal(err);
		throw err;
	} else {
		console.log('rScout started. Listening on *:3824');
	}
});
	
//Ensures that an event is not created twice to avoid non-unique table names
//Create empty tables for event
function addEvent(eventKey) {
	var sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='rscout'";
	con.query(sql, function (err, result) {
		var alreadyExists = false;
		if (err) {
			logger.fatal(err);
			throw err;
		};
		for (var i = 0; i < result.length; i++) {
			console.log("Checking Table")
			if (result[i].TABLE_NAME.includes(eventKey)) {
				alreadyExists = true;
			}
		}
		if (alreadyExists) {
			logger.crit("Tried to create event which already exists");
		} else {
			var sql = "CREATE TABLE " + eventKey + "teamstats (teamNumber int, name varchar(255), matchesPlayed int, autoCrossedTotal int, autoCrossedAverage int, autoEventTotals varchar(511), autoEventAverages varchar(511), teleopEventTotals varchar(511), teleopEventAverages varchar(511), climbingStateTotals varchar(511), climbingStateAverages varchar(511), climbingMethodTotals varchar(511), climbingMethodAverages varchar(511), foulTotal int, foulAverage double, techFoulTotal int, techFoulAverage double, yellowCardTotal int, yellowCardAverage double, redCardTotal int, redCardAverage double, noShowTotal int, noShowAverage double, DQTotal int, DQAverage double)";
			con.query(sql, function (err, result) {
				if (err) {
					logger.fatal(err);
					throw err;
				};
				console.log("Created Team Table for event " + eventKey);
			});
			var sql = "CREATE TABLE " + eventKey + "matchschedule (matchKey varchar(255), blue1TeamNumber int, blue2TeamNumber int, blue3TeamNumber int, red1TeamNumber int, red2TeamNumber int, red3TeamNumber int)";
			con.query(sql, function (err, result) {
				if (err) {
					logger.fatal(err);
					throw err;
				};
				console.log("Created Match Table for event " + eventKey);
			});
			var sql = "CREATE TABLE " + eventKey + "pitdata (teamNumber int, width int, length int, height int, weight int, driveTrain varchar(255), programmingLanguage varchar(255), cims int, notes varchar(511))";
			con.query(sql, function (err, result) {
				if (err) {
					logger.fatal(err);
					throw err;
				};
				console.log("Created Pit Data Table for event " + eventKey);
			});
			var sql = "CREATE TABLE " + eventKey + "matchdata (teamNumber int, matchKey varchar(255), crossedAutoLine boolean, autoEvents varchar(1027), teleopEvents varchar(1027), climbingState varchar(255), climbingMethod varchar(255), fouls int, techFouls int, yellowCard boolean, redCard boolean, notes varchar(511), DQ boolean, noShow boolean)";
			con.query(sql, function (err, result) {
				if (err) {
					logger.fatal(err);
					throw err;
				};
				console.log("Created Match Data Table for event " + eventKey);
			});
			var sql = "CREATE TABLE " + eventKey + "supermatchdata (matchKey varchar(255), boostCubes int, forceCubes int, levitateCubes int, notes varchar(255))";
			con.query(sql, function (err, result) {
				if (err) {
					logger.fatal(err);
					throw err;
				};
				console.log("Created Super Match Data Table for event " + eventKey);
			});
		}
	});
}

//Fill teams table
function addTeam(teamInfo, eventKey) {
	var climbingStateTotalsObject = {};
	var sql = "INSERT INTO " + eventKey + "teamstats (teamNumber, name, matchesPlayed, autoCrossedTotal, autoCrossedAverage, autoEventTotals, autoEventAverages, teleopEventTotals, teleopEventAverages, climbingStateTotals, climbingStateAverages, climbingMethodTotals, climbingMethodAverages, foulTotal, foulAverage, techFoulTotal, techFoulAverage, yellowCardTotal, yellowCardAverage, redCardTotal, redCardAverage, noShowTotal, noShowAverage, DQTotal, DQAverage) VALUES ('" + teamInfo.team_number + "', '" + mysql_real_escape_string(teamInfo.nickname) + "', '0', '0', '0', '" + JSON.stringify({placed: 0, dropped: 0, launchSuccess: 0, launchFailure: 0, pickedUp: 0}) + "', '" + JSON.stringify({placed: 0, dropped: 0, launchSuccess: 0, launchFailure: 0, pickedUp: 0}) + "', '" + JSON.stringify({placed: 0, dropped: 0, launchSuccess: 0, launchFailure: 0, pickedUp: 0}) + "', '" + JSON.stringify({placed: 0, dropped: 0, launchSuccess: 0, launchFailure: 0, pickedUp: 0}) + "', '" + JSON.stringify({noAttempt: 0, parkedOnPlatform: 0, didNotFinishInTime: 0, robotFell: 0, successful: 0}) + "', '" + JSON.stringify({noAttempt: 0, parkedOnPlatform: 0, didNotFinishInTime: 0, robotFell: 0, successful: 0}) + "', '" + JSON.stringify({climbOnRung: 0, climbOnRungWithOne: 0, climbOnRungWithTwo: 0, climbOnRungOfOtherBot: 0, climbOnPlatformOfOtherBot: 0, supportOneOnPlatform: 0, supportTwoOnPlatform: 0, creditThroughFoul: 0, creditThroughLevitate: 0}) + "', '" + JSON.stringify({climbOnRung: 0, climbOnRungWithOne: 0, climbOnRungWithTwo: 0, climbOnRungOfOtherBot: 0, climbOnPlatformOfOtherBot: 0, supportOneOnPlatform: 0, supportTwoOnPlatform: 0, creditThroughFoul: 0, creditThroughLevitate: 0}) + "', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0')";
	con.query(sql, function (err, result) {
		if (err) {
			logger.fatal(err);
			throw err;
		};
		console.log("1 team record inserted");
	});
}
//Fill matchSchedule table
function addMatch(matchInfo, eventKey) {
	
	var matchKey = matchInfo.key.substr(eventKey.length + 1);
	
	var red1 = matchInfo.alliances.red.team_keys[0].substr(3);
	var red2 = matchInfo.alliances.red.team_keys[1].substr(3);
	var red3 = matchInfo.alliances.red.team_keys[2].substr(3);
	var blue1 = matchInfo.alliances.blue.team_keys[0].substr(3);
	var blue2 = matchInfo.alliances.blue.team_keys[1].substr(3);
	var blue3 = matchInfo.alliances.blue.team_keys[2].substr(3);
	
	var sql = "INSERT INTO " + eventKey + "matchschedule (matchKey, blue1TeamNumber, blue2TeamNumber, blue3TeamNumber, red1TeamNumber, red2TeamNumber, red3TeamNumber) VALUES ('" + matchKey + "', '" + red1 + "', '" + red2 + "', '" + red3 + "', '" + blue1 + "', '" + blue2 + "', '" + blue3 + "')";
	con.query(sql, function (err, result) {
		if (err) {
			logger.fatal(err);
			throw err;
		};
		console.log("1 match inserted");
	});
}
