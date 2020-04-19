"use strict";

var _mongoose = _interopRequireDefault(require("mongoose"));

var _User = _interopRequireDefault(require("../models/User"));

var _ContactInfo = _interopRequireDefault(require("../models/ContactInfo"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

exports.getUser = function (req, res) {
  _User["default"].find().select().exec().then(function (users) {
    var response = {
      count: users.length,
      result: users
    };
    res.status(200).json(response);
  })["catch"](function (err) {
    res.status(500).json({
      error: err
    });
  });
};