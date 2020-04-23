"use strict";

var _mongoose = _interopRequireDefault(require("mongoose"));

var _User = _interopRequireDefault(require("../models/User"));

var _config = _interopRequireDefault(require("../../config/config"));

var _ContactInfo = _interopRequireDefault(require("../models/ContactInfo"));

var _locus = _interopRequireDefault(require("locus"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

exports.getUsers = function (req, res) {
  _User["default"].find().select().exec().then(function (users) {
    //   const response = {
    //     count: users.length,
    //     result: users,
    //   };
    res.status(200).json(users);
  })["catch"](function (err) {
    res.status(500).json({
      error: err
    });
  });
};

exports.getUser = function (req, res) {
  _User["default"].findOne({
    _id: req.params.id
  }).exec().then(function (user) {
    if (user) {
      res.status(200).json({
        _id: user._id,
        email: user.email,
        password: user.password,
        displayName: user.displayName,
        image: user.image,
        skills: user.skills,
        available: user.available,
        contactInfoList: user.contactInfoList
      });
    } else res.status(404).json({
      message: "No such user with this ID."
    });
  })["catch"](function (err) {
    res.status(500).json({
      error: err
    });
  });
};

exports.addUser = function (req, res) {
  var user = new _User["default"]({
    _id: new _mongoose["default"].Types.ObjectId(),
    email: req.body.email,
    password: req.body.password,
    displayName: req.body.displayName,
    image: req.body.image,
    skills: req.body.skills,
    available: req.body.available
  });
  user.save().then(function (user) {
    res.status(201).json({
      message: "Created user successfully",
      createdUser: {
        _id: user._id,
        email: user.email,
        password: user.password,
        displayName: user.displayName,
        image: user.image,
        skills: user.skills,
        available: user.available,
        request: {
          type: "GET",
          url: _config["default"].hostUrl + "users/" + user._id
        }
      }
    });
  })["catch"](function (err) {
    res.status(500).json({
      error: err
    });
  });
};