import { createApp } from 'vue';
import App from './App.vue';
import vuetify from './plugins/vuetify';
import router from './router';
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'

import { loadFonts } from './plugins/webfontloader';

loadFonts();

const pinia = createPinia(); 
pinia.use(piniaPluginPersistedstate);

createApp(App)
  .use(router)
  .use(vuetify)
  .use(VueDatePicker)
  .use(pinia)
  .mount('#app');
