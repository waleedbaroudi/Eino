"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports["default"] = void 0;

var _categoryController = _interopRequireDefault(require("../controllers/categoryController"));

var _express = _interopRequireDefault(require("express"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

var router = _express["default"].Router(); //Get a list of all users


router.get("/", _categoryController["default"].getCategories); //Get a particular user with their unique ID

router.get("/:id", _categoryController["default"].getCategory); //Add a user and get that added user back as a response

router.post("/", _categoryController["default"].addCategory);
var _default = router;
exports["default"] = _default;