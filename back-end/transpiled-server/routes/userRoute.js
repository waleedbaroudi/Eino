"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports["default"] = void 0;

var _userController = _interopRequireDefault(require("../controllers/userController"));

var _express = _interopRequireDefault(require("express"));

var _multer = _interopRequireDefault(require("multer"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

var storage = _multer["default"].diskStorage({
  //Adjust how files get stored
  destination: function destination(req, file, cb) {
    cb(null, "./uploads/");
  },
  filename: function filename(req, file, cb) {
    cb(null, new Date().toISOString() + file.originalname);
  }
});

var fileFilter = function fileFilter(req, file, cb) {
  //reject a file
  if (file.mimetype === "image/jpeg" || file.mimetype === "image/png") {
    cb(null, true);
  } else {
    cb(new Error('Invalid image type'), false);
  }
};

var upload = (0, _multer["default"])({
  storage: storage,
  limits: {
    fileSize: 1024 * 1024 / 2 //Only half a megabyte maximum

  },
  fileFilter: fileFilter
});

var router = _express["default"].Router(); //Get a list of all users


router.get("/", _userController["default"].getUsers); //Get a particular user with their unique ID

router.get("/:id", _userController["default"].getUser); //Add a user and get that added user back as a response

router.post("/", upload.single("userImage"), _userController["default"].addUser); //Add the skills to the skills array in the User object

router.patch("/:email", _userController["default"].addUserSkills); //Remove a user by passing their id as a paramter

router["delete"]("/:id", _userController["default"].deleteUser);
var _default = router;
exports["default"] = _default;