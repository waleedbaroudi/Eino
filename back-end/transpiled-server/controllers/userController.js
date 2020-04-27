"use strict";

var _mongoose = _interopRequireDefault(require("mongoose"));

var _User = _interopRequireDefault(require("../models/User"));

var _config = _interopRequireDefault(require("../../config/config"));

var _locus = _interopRequireDefault(require("locus"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

function _toConsumableArray(arr) { return _arrayWithoutHoles(arr) || _iterableToArray(arr) || _unsupportedIterableToArray(arr) || _nonIterableSpread(); }

function _nonIterableSpread() { throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method."); }

function _unsupportedIterableToArray(o, minLen) { if (!o) return; if (typeof o === "string") return _arrayLikeToArray(o, minLen); var n = Object.prototype.toString.call(o).slice(8, -1); if (n === "Object" && o.constructor) n = o.constructor.name; if (n === "Map" || n === "Set") return Array.from(n); if (n === "Arguments" || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)) return _arrayLikeToArray(o, minLen); }

function _iterableToArray(iter) { if (typeof Symbol !== "undefined" && Symbol.iterator in Object(iter)) return Array.from(iter); }

function _arrayWithoutHoles(arr) { if (Array.isArray(arr)) return _arrayLikeToArray(arr); }

function _arrayLikeToArray(arr, len) { if (len == null || len > arr.length) len = arr.length; for (var i = 0, arr2 = new Array(len); i < len; i++) { arr2[i] = arr[i]; } return arr2; }

exports.getUsers = function (req, res) {
  _User["default"].find().select().exec().then(function (users) {
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
    image: req.file.path,
    skills: [],
    available: req.body.available,
    contactInfoList: req.body.contactInfoList
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
        contactInfoList: user.contactInfoList,
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

exports.addUserSkills = function (req, res) {
  _User["default"].findOneAndUpdate({
    email: req.params.email
  }, {
    $push: {
      skills: req.body.skills
    }
  }).exec().then(function (user) {
    var _user$skills;

    res.status(200).json({
      message: "Skills were added to the users object",
      skills: (_user$skills = user.skills).concat.apply(_user$skills, _toConsumableArray(req.body.skills)) //?concat and the spread operator to print the current lost of skilss

    });
  });
}; //takes a type on the parameter


exports.deleteUser = function (req, res) {
  _User["default"].remove({
    _id: req.params.id
  }).exec().then(function (user) {
    res.status(200).json({
      message: "User Deleted",
      request: {
        type: "GET",
        url: _config["default"].hostUrl + "/users"
      }
    });
  })["catch"](function (err) {
    res.status(500).json({
      error: err
    });
  });
};