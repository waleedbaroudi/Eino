"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports["default"] = void 0;

var _userController = _interopRequireDefault(require("../controllers/userController"));

var _express = _interopRequireDefault(require("express"));

var _storage = require("@google-cloud/storage");

var _path = _interopRequireDefault(require("path"));

var _multer = _interopRequireDefault(require("multer"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

var _require = require('util'),
    format = _require.format;

var gc = new _storage.Storage({
  keyFilename: _path["default"].join(__dirname, '../../eino-275710-13520d32986b.json'),
  projectId: 'eino-275710'
});
var einoImagesBucket = gc.bucket('einoimages'); // const multerStorage = multer.diskStorage({
//   //Adjust how files get stored
//   destination: (req, file, cb) => {
//     cb(null, "./uploads/");
//   },
//   filename: (req, file, cb) => {
//     cb(null, new Date().toISOString().replace(/:/g, '-') + file.originalname);
//   },
// });

var multer = (0, _multer["default"])({
  storage: _multer["default"].memoryStorage(),
  limits: {
    fileSize: 5 * 1024 * 1024 // no larger than 5mb, you can change as needed.

  }
});

var fileFilter = function fileFilter(req, file, cb) {
  //reject a file
  if (file.mimetype === "image/jpeg" || file.mimetype === "image/png") {
    cb(null, true);
  } else {
    cb(new Error('Invalid image type'), false);
  }
}; // const upload = multer({
//   storage: multerStorage,
//   limits: {
//     fileSize: (1024 * 1024) / 2, //Only half a megabyte maximum
//   },
//   fileFilter: fileFilter
// });


var router = _express["default"].Router(); //Get a list of all users


router.get("/", _userController["default"].getUsers); //Get a particular user with their unique ID

router.get("/:id", _userController["default"].getUser); //Add a user and get that added user back as a response
//router.post("/", upload.single("userImage"), userController.addUser);

router.post("/", multer.single("userImage"), function (req, res, next) {
  if (!req.file) {
    res.status(400).send('No file uploaded.');
    return;
  } // Create a new blob in the bucket and upload the file data.


  var blob = einoImagesBucket.file(req.file.originalname);
  var blobStream = blob.createWriteStream({
    metadata: {
      contentType: "image/jpeg"
    }
  });
  blobStream.on('error', function (err) {
    next(err);
  });
  blobStream.on('finish', function () {
    // The public URL can be used to directly access the file via HTTP.
    var publicUrl = format("https://storage.googleapis.com/".concat(einoImagesBucket.name, "/").concat(blob.name));
    res.status(200).send(publicUrl);
  });
  blobStream.end(req.file.buffer);
}); //Add the skills to the skills array in the User object

router.patch("/:email", _userController["default"].addUserSkills); //Remove a user by passing their id as a paramter

router["delete"]("/:id", _userController["default"].deleteUser);
var _default = router;
exports["default"] = _default;