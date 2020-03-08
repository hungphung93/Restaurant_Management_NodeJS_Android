import mongoose from 'mongoose';
import * as databaseUtilities from '../shared/utilities/databaseUtilities';
import * as mongoDBEntityNames from '../shared/constants/mongoDBEntityNames';
import { TableStatus } from '../shared/enum/TableStatus';

const TableSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    status: {
        type: String,
        enum: [TableStatus.EMPTY, TableStatus.SERVING],
        default: TableStatus.EMPTY
    },
    open_at: {
        type: Date,
        default: null
    }
});

export const TableEntity = databaseUtilities.getEntity(mongoDBEntityNames.tableCollection, TableSchema);
