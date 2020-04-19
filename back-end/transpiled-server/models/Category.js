"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports["default"] = void 0;

var _mongoose = _interopRequireDefault(require("mongoose"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

var categorySchema = _mongoose["default"].Schema({
  _id: _mongoose["default"].Schema.Types.ObjectId,
  type: {
    type: String,
    required: true
  },
  subCategories: [String] //Optional field

});

var _default = _mongoose["default"].model('Category', categorySchema);

exports["default"] = _default;