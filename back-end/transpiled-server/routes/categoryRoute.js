"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports["default"] = void 0;

var _categoryController = _interopRequireDefault(require("../controllers/categoryController"));

var _express = _interopRequireDefault(require("express"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

var router = _express["default"].Router(); //Get a list of all users


router.get("/", _categoryController["default"].getCategories); //Get a list of user objects in a category by the type of the category

router.get("/:type", _categoryController["default"].getUsersInCategory); //Get a list of user Ids in a category by the type of the category

router.get("/id/:type", _categoryController["default"].getUserIdsInCategory); //Add a category and get that added category back as a response

router.post("/", _categoryController["default"].addCategory); //Add a user ID to a categories

router.patch("/:type", _categoryController["default"].updateCategory); //Remove a category depending on type

router["delete"]("/:type", _categoryController["default"].deleteCategory);
var _default = router;
exports["default"] = _default;