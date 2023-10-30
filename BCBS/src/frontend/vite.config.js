import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  // https://vitejs.dev/config/server-options.html
  server: {
    port: 3001,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      }
    }
  }
})
