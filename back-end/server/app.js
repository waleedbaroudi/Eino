import http from 'http';
import express from 'express';
import morgan from 'morgan';
import mongoose from 'mongoose';
import config from "../config/config";

//Server Setup
const port = 3000 || process.env.PORT;
const app = express();
const server = http.createServer(app);

//Express middlewear
//Funnel all requests through morgan to be logged to the console.
app.use(morgan("dev"));

//Parse the data from the requests we get so we can extract them.
app.use(express.urlencoded({ extended: false }));
app.use(express.json());

//Establish a connection with the mongodb server.
mongoose.connect(config.dbConnectionString, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});
mongoose.set("useCreateIndex", true);
mongoose.set("useFindAndModify", false);
//When the connection is successful log it to the console
mongoose.connection.on("open", (open) => {
  console.log("Connected to mongo server successfully.");
});

//To prevent CORS errors
//We need to append headers before the response is sent back to the client
//These headers tell the browser that we allow a client has a different origin from our server to get the response.
app.use((req, res, next) => {
  res.header("Access-Control-Allow-Origin", "*");
  //* Every origin that a request comes from is allowed, this can be restricted to specific ips like 'http:/website.com
  res.header(
    "Access-Control-Allow-Headers",
    "Origin, X-Requested-With, Content-Type, Accept, Authorization"
  );
  //The options request which is an HTTP method is always sent first and once by the browser
  if (req.method === "OPTIONS") {
    res.header("Access-Control-Allow-Methods", "PUT, POST, PATCH, DELETE, GET");
    return res.status(200).json({});
  }
  next();
});

app.get("/", (req, res, next) => {
  res.send("Testing root endpoint");
});

//HANDLING ERRORS
//if the program reaches this line that means no router in products or orders was able to handle the request therefore we catch an err
app.use((req, res, next) => {
  const error = new Error("Not found");
  error.status = 404;
  //forward the error
  next(error);
});

//this line handles error thrown by the previous block or any request that reaches this line.
app.use((error, req, res, next) => {
  res.status(error.status || 500);
  res.json({
    error: {
      message: error.message,
    },
  });
});

console.log(`Listening on port: ${port}`);
//Start listening for requests on the specified PORT
server.listen(port);
