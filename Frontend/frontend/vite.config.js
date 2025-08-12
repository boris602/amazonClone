// vite.config.js
import { defineConfig } from 'vite';
import { resolve } from 'path';

export default defineConfig(() => ({
  root: './src/pages',
  publicDir: '../../public',
  appType: 'mpa',
  server: {
    open: '/homepage/index.html',
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
  build: {
    outDir: '../../dist',
    emptyOutDir: true,
    rollupOptions: {
      input: {
        homepage: resolve(__dirname, 'src/pages/homepage/index.html'),
        addItemPage: resolve(__dirname, 'src/pages/AddItemPage/index.html'),
        loginPage: resolve(__dirname, 'src/pages/LoginPage/index.html'),
        createAccPage: resolve(__dirname, 'src/pages/CreateAccountPage/index.html'),
      },
    },
  },
}));
