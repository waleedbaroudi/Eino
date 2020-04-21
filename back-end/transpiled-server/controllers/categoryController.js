"use strict";

var _mongoose = _interopRequireDefault(require("mongoose"));

var _Category = _interopRequireDefault(require("../models/Category"));

var _config = _interopRequireDefault(require("../../config/config"));

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

exports.getCategory = function (req, res) {
  _Category["default"].findOne({
    _id: req.params.id
  }).exec().then(function (category) {
    if (category) {
      res.status(200).json({
        _id: category._id,
        type: category.type,
        subCategories: category.subCategories
      });
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
    subCategories: req.body.subCategories
  });
  category.save().then(function (category) {
    res.status(201).json({
      message: "Created category successfully",
      createdCategory: {
        _id: category._id,
        type: category.type,
        subCategories: category.subCategories,
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