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
}; //Add a user and returns the added user
// _id: mongoose.Schema.Types.ObjectId,
// email: {type: String, required: true},
// password: {type: String, required: true},
// displayName: {type: String, required: true},
// image: String,
// skills: [String],
// avaiable: Boolean,
// contactInfoList: [{type: mongoose.Schema.Types.ObjectId, ref: 'ContactInfo'}]


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
          url: "http://localhost:3000/api/" + user._id
        }
      }
    });
  })["catch"](function (err) {
    res.status(500).json({
      error: err
    });
  });
};