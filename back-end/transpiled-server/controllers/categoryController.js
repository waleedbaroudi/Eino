"use strict";

var _mongoose = _interopRequireDefault(require("mongoose"));

var _Category = _interopRequireDefault(require("../models/Category"));

var _config = _interopRequireDefault(require("../../config/config"));

var _locus = _interopRequireDefault(require("locus"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

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
};