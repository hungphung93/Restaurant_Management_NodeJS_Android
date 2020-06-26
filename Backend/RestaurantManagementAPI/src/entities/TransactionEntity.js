import mongoose from 'mongoose';
import * as databaseUtilities from '../shared/utilities/databaseUtilities';
import * as mongoDBEntityNames from '../shared/constants/mongoDBEntityNames';
import { TransactionStatus } from '../shared/enum/TransactionStatus';

const TransactionSchema = new mongoose.Schema({
    table_name: {
        type: String,
        required: true
    },
    status: {
        type: String,
        enum: [TransactionStatus.PROCESSING, TransactionStatus.COMPLETED],
        default: TransactionStatus.PROCESSING
    },
    open_at: {
        type: Date,
        default: Date.now()
    },
    ordered_foods: [mongoose.Schema.Types.Mixed],
    total_amount: {
        type: Number,
        default: 0
    }
});

export const TransactionEntity = databaseUtilities.getEntity(mongoDBEntityNames.transactionCollection, TransactionSchema);
