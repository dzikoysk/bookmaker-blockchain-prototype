module.exports = {
  webpack: (config, { isServer }) => {
      config.optimization.minimize = false
      config.optimization.minimizer = []

      if (!isServer) {
        config.resolve.fallback.fs = false
      }

      return config
  },
  output: 'export',
  // distDir: '../docs',
  reactStrictMode: true
}