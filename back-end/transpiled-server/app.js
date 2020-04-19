"use strict";

var _http = _interopRequireDefault(require("http"));

var _express = _interopRequireDefault(require("express"));

var _morgan = _interopRequireDefault(require("morgan"));

var _mongoose = _interopRequireDefault(require("mongoose"));

var _config = _interopRequireDefault(require("../config/config"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

//Server Setup
var port = 3000 || process.env.PORT;
var app = (0, _express["default"])();

var server = _http["default"].createServer(app); //Express middlewear
//Funnel all requests through morgan to be logged to the console.


app.use((0, _morgan["default"])("dev")); //Parse the data from the requests we get so we can extract them.

app.use(_express["default"].urlencoded({
  extended: false
}));
app.use(_express["default"].json()); //Establish a connection with the mongodb server.

_mongoose["default"].connect(_config["default"].dbConnectionString, {
  useNewUrlParser: true,
  useUnifiedTopology: true
});

_mongoose["default"].set("useCreateIndex", true);

_mongoose["default"].set("useFindAndModify", false); //When the connection is successful log it to the console


_mongoose["default"].connection.on("open", function (open) {
  console.log("Connected to mongo server successfully.");
}); //To prevent CORS errors
//We need to append headers before the response is sent back to the client
//These headers tell the browser that we allow a client has a different origin from our server to get the response.


app.use(function (req, res, next) {
  res.header("Access-Control-Allow-Origin", "*"); //* Every origin that a request comes from is allowed, this can be restricted to specific ips like 'http:/website.com

  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization"); //The options request which is an HTTP method is always sent first and once by the browser

  if (req.method === "OPTIONS") {
    res.header("Access-Control-Allow-Methods", "PUT, POST, PATCH, DELETE, GET");
    return res.status(200).json({});
  }

  next();
});
app.get("/", function (req, res, next) {
  res.send("Testing root endpoint");
}); //HANDLING ERRORS
//if the program reaches this line that means no router in products or orders was able to handle the request therefore we catch an err

app.use(function (req, res, next) {
  var error = new Error("Not found");
  error.status = 404; //forward the error

  next(error);
}); //this line handles error thrown by the previous block or any request that reaches this line.

app.use(function (error, req, res, next) {
  res.status(error.status || 500);
  res.json({
    error: {
      message: error.message
    }
  });
});
console.log("Listening on port: ".concat(port)); //Start listening for requests on the specified PORT

server.listen(port);