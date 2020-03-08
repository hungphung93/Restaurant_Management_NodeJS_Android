import winston from 'winston';
import config from '../../../config.json';



const transport = new winston.transports.File({
    filename: config.logger.errorLog,
    level: 'error',
    handleExceptions: true
});

const createLogFormatter = () => {
    const { combine, timestamp, printf } = winston.format;

    return combine(
        timestamp(),
        printf(info => {
            const { req, res } = info.message;
            return `${info.timestamp} ${info.level} : ${req} : ${info.message}`;
        })
    );
}

export const logger = winston.createLogger({
    format: createLogFormatter(),
    transports: transport
});
