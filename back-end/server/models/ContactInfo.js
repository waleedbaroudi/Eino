import mongoose, { Schema, mongo } from 'mongoose';

const contactInfoSchema = mongoose.Schema({
    user: {type: mongoose.Schema.Types.ObjectId, ref: 'User'},
    type: {type: String, required: true},
    info: {type: String, required: true}
});

export default mongoose.model('ContactInfo', contactInfoSchema);