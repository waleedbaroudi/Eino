import mongoose from 'mongoose';

const categorySchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    type: {type: String, required: true},
    subCategories: [String],//Optional field
    users: [{type: mongoose.Schema.Types.ObjectId, ref: 'User'}]
});

export default mongoose.model('Category', categorySchema);