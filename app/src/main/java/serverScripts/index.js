

/*
TO INTERACT WITH DB, USE FORMATS BELOW

Ping Server:
localhost:3824/?action=ping

Get All Data for event:
localhost:3824/?action=getMatchData&eventKey=******

Get Match Data:
localhost:3824/?action=getMatchData&eventKey=******&matchKey=******&teamNumber=******

Insert Match Data:
localhost:3824/?action=insertMatchData&eventKey=******&data=******

data is a JSON object containing:
teamNumber int
matchKey varchar(255)
autoCrossed boolean
autoCubes varchar(511)
teleopCubes varchar(511)
climbingState varchar(255)
climbingMethod varchar(255)
fouls int
techFouls int
yellowCard boolean
redCard boolean
notes varchar(511)
EXAMPLE:
http://localhost:3824/?action=insertMatchData&eventKey=2017tnkn&data={%20%22teamNumber%22:1287,%20%22matchKey%22:%22qm1%22,%20%22autoCrossed%22:1,%20%22autoCrossed%22:1,%20%22autoCubes%22:%22CUBES%20PLACEHOLDER%22,%20%22teleopCubes%22:%22CUBES%20PLACEHOLDER%22,%20%22climbingState%22:%22No%20Attempt%22,%20%22climbingMethod%22:null,%20%22fouls%22:1,%20%22techFouls%22:8,%20%22yellowCard%22:1,%20%22redCard%22:0,%20%22notes%22:%22notes%22}
*/

//Define things for NodeJS
var app = require('express')();
var http = require('http').Server(app);
var url = require('url');
var io = require('socket.io')(http);
var mysql = require('mysql');

// Connect to DB
var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "rscout",
  database: "rscout"
});

var request;

//Loads HTML file
app.get('/', function(req, res){
	res.sendFile(__dirname + '/index.html');
	if (url.parse(req.url, true).query != null) {
		request = url.parse(req.url, true).query;
		console.log(request);
		switch(request.action) {
			case null:
				break;
			case 'ping':
				res.send('pong');
				break;
			case 'getFullDB':
				var result = {matchData:'', matchSchedule:'', pitData:'', teamStats:''};
				var sql = "SELECT * FROM " + request.eventKey + "matchdata";
				con.query(sql, function (err, result) {
					if (err) { throw err;};
					JSONresult = JSON.stringify(result[0]);
					result.matchData = JSONresult;
					var sql = "SELECT * FROM " + request.eventKey + "matchschedule";
					con.query(sql, function (err, result) {
						if (err) { throw err;};
						JSONresult = JSON.stringify(result[0]);
						result.matchSchedule = JSONresult;
						var sql = "SELECT * FROM " + request.eventKey + "pitdata";
						con.query(sql, function (err, result) {
							if (err) { throw err;};
							JSONresult = JSON.stringify(result[0]);
							result.pitdata = JSONresult;
							var sql = "SELECT * FROM " + request.eventKey + "teamstats";
							con.query(sql, function (err, result) {
								if (err) { throw err;};
								JSONresult = JSON.stringify(result[0]);
								result.teamstats = JSONresult;
								res.send(result);
							});
						});
					});
				});
				break;
			case 'getMatchData':
				var sql = "SELECT * FROM " + request.eventKey + "matchdata WHERE matchKey='" + request.matchKey + "' AND teamNumber='" + request.teamNumber + "'";
				con.query(sql, function (err, result) {
					var alreadyExists = false;
					if (err) { throw err;};
					JSONresult = JSON.stringify(result[0]);
					console.log(JSONresult);
					res.send(JSONresult);
				});
				break;
			case 'insertMatchData':
				requestData = JSON.parse(request.data);
				console.log('InsertingMatchData');
				console.log(requestData);
				addMatchData(request.eventKey, requestData.teamNumber, requestData.matchKey, requestData.autoCrossed, requestData.autoCubes, requestData.teleopCubes, requestData.climbingState, requestData.climbingMethod, requestData.fouls, requestData.techFouls, requestData.yellowCard, requestData.redCard, requestData.notes);
				break;
			case 'getPitData':
				var sql = "SELECT * FROM " + request.eventKey + "pitdata WHERE teamNumber='" + request.teamNumber + "'";
				con.query(sql, function (err, result) {
					var alreadyExists = false;
					if (err) { throw err;};
					JSONresult = JSON.stringify(result[0]);
					console.log(JSONresult);
					res.send(JSONresult);
				});
				break;
			case 'insertPitData':
				requestData = JSON.parse(request.data);
				console.log('InsertingPitData');
				console.log(requestData);
				addPitData(request.eventKey, requestData.teamNumber, requestData.width, requestData.length, requestData.height, requestData.weight, requestData.driveTrain, requestData.programmingLanguage, requestData.cims, requestData.notes);
				break;
			default:
				console.log('Error! Command not recognized');
				break;
		}
	}
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
function addMatchData(eventKey, teamNumber, matchKey, autoCrossed, autoCubes, teleopCubes, climbingState, climbingMethod, fouls, techFouls, yellowCard, redCard, notes) {
	var alreadyExists = false;
	var sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='rscout'";
	con.query(sql, function (err, result) {
		if (err) { throw err; } else {console.log("foundTables")};
		for (var i = 0; i < result.length; i++) {
			console.log("Checking Table")
			if (result[i].TABLE_NAME.includes(eventKey)) {
				alreadyExists = true;
			}
		}
		if (!alreadyExists) {
		console.log("Error! Event has not been created");
		} else {
			var sql = "INSERT INTO " + eventKey + "matchdata (teamNumber, matchKey, autoCrossed, autoCubes, teleopCubes, climbingState, climbingMethod, fouls, techFouls, yellowCard, redCard, notes) VALUES ('" + teamNumber + "', '" + matchKey + "', '" + autoCrossed + "','" + autoCubes + "','" + teleopCubes + "','" + climbingState + "','" + climbingMethod + "','" + fouls + "','" + techFouls + "','" + yellowCard + "','" + redCard + "','" + mysql_real_escape_string(notes) + "')";
			con.query(sql, function (err, result) {
				if (err) throw err;
				console.log("1 match data record inserted");
			});
			var sql = "SELECT * FROM " + eventKey + "teamstats WHERE teamNumber = '" + teamNumber + "'";
			con.query(sql, function (err, result1) {
				if (err) throw err;
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

				console.log(result1);
				var sql = "UPDATE " + eventKey + "teamstats SET matchesPlayed = '" + (result1[0].matchesPlayed + 1) + "', autoCrossedTotal = '" + (result1[0].autoCrossedTotal + autoCrossed) + "', autoCubesTotals = 'CUBES PLACEHOLDER', teleopCubesTotals = 'CUBES PLACEHOLDER', climbingStateTotals = 'CLIMBING PLACEHOLDER', climbingMethodTotals = 'CLIMBING PLACEHOLDER', foulTotal = '" + (result1[0].foulTotal + fouls) + "', techFoulTotal = '" + (result1[0].techFoulTotal + techFouls) + "', yellowCardTotal = '" + (result1[0].yellowCardTotal + yellowCards) + "', redCardTotal = '" + (result1[0].redCardTotal + redCards) + "' WHERE teamNumber = '" + teamNumber + "';";
				con.query(sql, function (err, result) {
					if (err) throw err;
					console.log("1 team record updated");
				});
			});
		}
	});
};
//Insert Super Match Data
function addSuperMatchData(eventKey, matchKey, boostCubes, forceCubes, levitateCubes, notes) {
	var alreadyExists = false;
	var sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='rscout'";
	con.query(sql, function (err, result) {
		if (err) { throw err; } else {console.log("foundTables")};
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
				if (err) throw err;
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
		if (err) { throw err; } else {console.log("foundTables")};
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
				if (err) throw err;
				console.log("1 pit data record inserted");
			});
		}
	});
};

//Listen on port 3824
http.listen(3824, function(err){
	if (err) {
		throw err;
	} else {
		console.log('rScout started. Listening on *:3824');
	}
});

//Checks user connections and disconnections
io.on('connection', function(socket){

	console.log('User connected');
	socket.on('disconnect', function(){
		console.log('User disconnected');
	});

	//Ensures that an event is not created twice to avoid non-unique table names
	socket.on('eventRequest', function(eventKey){
		console.log('requesting event ' + eventKey);
		var sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='rscout'";
		con.query(sql, function (err, result) {
			var alreadyExists = false;
			if (err) { throw err; } else {console.log("foundTables")};
				for (var i = 0; i < result.length; i++) {
					console.log("Checking Table")
					if (result[i].TABLE_NAME.includes(eventKey)) {
						alreadyExists = true;
					}
				}
			io.emit("requestEvent", eventKey, alreadyExists);
		});
	});

	//Create empty tables for event
	socket.on('addEvent', function(eventKey) {
		var sql = "CREATE TABLE " + eventKey + "teamstats (teamNumber int, name varchar(255), matchesPlayed int, autoCrossedTotal int, autoCubesTotals varchar(511), teleopCubesTotals varchar(511), climbingStateTotals varchar(511), climbingMethodTotals varchar(511), foulTotal int, techFoulTotal int, yellowCardTotal int, redCardTotal int)";
		con.query(sql, function (err, result) {
			if (err) throw err;
			console.log("Created Team Table for event " + eventKey);
		});
		var sql = "CREATE TABLE " + eventKey + "matchschedule (matchKey varchar(255), blue1TeamNumber int, blue2TeamNumber int, blue3TeamNumber int, red1TeamNumber int, red2TeamNumber int, red3TeamNumber int)";
		con.query(sql, function (err, result) {
			if (err) throw err;
			console.log("Created Match Table for event " + eventKey);
		});
		var sql = "CREATE TABLE " + eventKey + "pitdata (teamNumber int, width int, length int, height int, weight int, driveTrain varchar(255), programmingLanguage varchar(255), cims int, notes varchar(511))";
		con.query(sql, function (err, result) {
			if (err) throw err;
			console.log("Created Pit Data Table for event " + eventKey);
		});
		var sql = "CREATE TABLE " + eventKey + "matchdata (teamNumber int, matchKey varchar(255), autoCrossed boolean, autoCubes varchar(511), teleopCubes varchar(511), climbingState varchar(255), climbingMethod varchar(255), fouls int, techFouls int, yellowCard boolean, redCard boolean, notes varchar(511))";
		con.query(sql, function (err, result) {
			if (err) throw err;
			console.log("Created Match Data Table for event " + eventKey);
		});
		var sql = "CREATE TABLE " + eventKey + "supermatchdata (matchKey varchar(255), boostCubes int, forceCubes int, levitateCubes int, notes(255))";
		con.query(sql, function (err, result) {
			if (err) throw err;
			console.log("Created Super Match Data Table for event " + eventKey);
		});
	});

	//Fill teams table
	socket.on('addTeam', function(teamInfo, eventKey) {
		var climbingStateTotalsObject = {};
		var sql = "INSERT INTO " + eventKey + "teamstats (teamNumber, name, matchesPlayed, autoCrossedTotal, autoCubesTotals, teleopCubesTotals, climbingStateTotals, climbingMethodTotals, foulTotal, techFoulTotal, yellowCardTotal, redCardTotal) VALUES ('" + teamInfo.team_number + "', '" + mysql_real_escape_string(teamInfo.nickname) + "', '0', '0', '', '', '', '', '0', '0', '0', '0')";
		con.query(sql, function (err, result) {
			if (err) throw err;
			console.log("1 team record inserted");
		});
	});

	//Fill matchSchedule table
	socket.on('addMatch', function(matchInfo, eventKey) {

		var matchKey = matchInfo.key.substr(eventKey.length + 1);

		var red1 = matchInfo.alliances.red.team_keys[0].substr(3);
		var red2 = matchInfo.alliances.red.team_keys[1].substr(3);
		var red3 = matchInfo.alliances.red.team_keys[2].substr(3);
		var blue1 = matchInfo.alliances.blue.team_keys[0].substr(3);
		var blue2 = matchInfo.alliances.blue.team_keys[1].substr(3);
		var blue3 = matchInfo.alliances.blue.team_keys[2].substr(3);

		var sql = "INSERT INTO " + eventKey + "matchschedule (matchKey, blue1TeamNumber, blue2TeamNumber, blue3TeamNumber, red1TeamNumber, red2TeamNumber, red3TeamNumber) VALUES ('" + matchKey + "', '" + red1 + "', '" + red2 + "', '" + red3 + "', '" + blue1 + "', '" + blue2 + "', '" + blue3 + "')";
		con.query(sql, function (err, result) {
			if (err) throw err;
			console.log("1 match inserted");
		});
	});
});