"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports["default"] = void 0;

var _userController = _interopRequireDefault(require("../controllers/userController"));

var _express = _interopRequireDefault(require("express"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

var router = _express["default"].Router(); //Get a list of all users


router.get("/", _userController["default"].getUser); //Add a user and get that added user back as a response

router.post("/", _userController["default"].addUser);
var _default = router;
exports["default"] = _default;