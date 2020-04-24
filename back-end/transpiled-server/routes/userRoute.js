"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports["default"] = void 0;

var _userController = _interopRequireDefault(require("../controllers/userController"));

var _express = _interopRequireDefault(require("express"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

var router = _express["default"].Router(); //Get a list of all users


router.get("/", _userController["default"].getUsers); //Get a particular user with their unique ID

router.get("/:id", _userController["default"].getUser); //Add a user and get that added user back as a response

router.post("/", _userController["default"].addUser); //Add the skills to the skills array in the User object

router.patch("/:email", _userController["default"].addUserSkills); //Remove a user by passing their id as a paramter

router["delete"]("/:id", _userController["default"].deleteUser);
var _default = router;
exports["default"] = _default;