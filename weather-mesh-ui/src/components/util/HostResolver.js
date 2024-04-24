const prod = {
    WEATHER_ORIGIN: 'http://localhost:8080',
    CIRCUIT_BREAKER_ORIGIN: 'http://localhost:8082',
    AUTH_ORIGIN: process.env.AUTH_BASE_URL
}

const dev = {
    WEATHER_ORIGIN: 'http://localhost:8080',
    CIRCUIT_BREAKER_ORIGIN: 'http://localhost:8082',
    AUTH_ORIGIN: 'http://localhost:8083'
}

export const config = process.env.NODE_ENV === 'dev' ? dev : dev