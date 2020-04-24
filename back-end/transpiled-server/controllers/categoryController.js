"use strict";

function _typeof(obj) { "@babel/helpers - typeof"; if (typeof Symbol === "function" && typeof Symbol.iterator === "symbol") { _typeof = function _typeof(obj) { return typeof obj; }; } else { _typeof = function _typeof(obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }; } return _typeof(obj); }

var _mongoose = _interopRequireWildcard(require("mongoose"));

var _Category = _interopRequireDefault(require("../models/Category"));

var _config = _interopRequireDefault(require("../../config/config"));

var _locus = _interopRequireDefault(require("locus"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

function _getRequireWildcardCache() { if (typeof WeakMap !== "function") return null; var cache = new WeakMap(); _getRequireWildcardCache = function _getRequireWildcardCache() { return cache; }; return cache; }

function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } if (obj === null || _typeof(obj) !== "object" && typeof obj !== "function") { return { "default": obj }; } var cache = _getRequireWildcardCache(); if (cache && cache.has(obj)) { return cache.get(obj); } var newObj = {}; var hasPropertyDescriptor = Object.defineProperty && Object.getOwnPropertyDescriptor; for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) { var desc = hasPropertyDescriptor ? Object.getOwnPropertyDescriptor(obj, key) : null; if (desc && (desc.get || desc.set)) { Object.defineProperty(newObj, key, desc); } else { newObj[key] = obj[key]; } } } newObj["default"] = obj; if (cache) { cache.set(obj, newObj); } return newObj; }

exports.getCategories = function (req, res) {
  _Category["default"].find().select().exec().then(function (categories) {
    return res.status(200).json(categories);
  })["catch"](function (err) {
    res.status(500).json({
      error: err
    });
  });
};

exports.getUsersInCategory = function (req, res) {
  _Category["default"].findOne({
    type: req.params.type
  }).populate("users").exec().then(function (category) {
    if (category) {
      //? for testing eval(locus);
      res.status(200).json(category.users);
    } else res.status(404).json({
      message: "No such Category with this ID."
    });
  })["catch"](function (err) {
    res.status(500).json({
      error: err
    });
  });
};

exports.addCategory = function (req, res) {
  var category = new _Category["default"]({
    _id: new _mongoose["default"].Types.ObjectId(),
    type: req.body.type,
    subCategories: req.body.subCategories,
    users: []
  });
  category.save().then(function (category) {
    res.status(201).json({
      message: "Created category successfully",
      createdCategory: {
        _id: category._id,
        type: category.type,
        subCategories: category.subCategories,
        users: [],
        request: {
          type: "GET",
          url: _config["default"].hostUrl + "/categories/" + category._id
        }
      }
    });
  })["catch"](function (err) {
    res.status(500).json({
      error: err
    });
  });
}; //take a body of _id:


exports.updateCategory = function (req, res) {
  _Category["default"].findOneAndUpdate({
    type: req.params.type
  }, {
    $push: {
      users: req.body._id
    }
  }).exec().then(function (category) {
    res.status(200).json({
      message: "user was added to the users array on ".concat(category.type, " object")
    });
  });
}; //takes a type on the parameter


exports.deleteCategory = function (req, res) {
  _Category["default"].remove({
    type: req.params.type
  }).exec().then(function (category) {
    res.status(200).json({
      message: "Category Deleted",
      request: {
        type: "GET",
        url: _config["default"].hostUrl + "/categories"
      }
    });
  })["catch"](function (err) {
    res.status(500).json({
      error: err
    });
  });
};