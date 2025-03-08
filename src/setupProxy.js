const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'https://q52dj2f4-8080.inc1.devtunnels.ms',
      changeOrigin: true,
    })
  );
};