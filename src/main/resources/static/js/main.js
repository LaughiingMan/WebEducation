import Vue from 'vue'
import UUID from 'vue-uuid'
import VueCookies from 'vue-cookies'
import Vuetify from 'vuetify'
import '@babel/polyfill'
import 'api/resource'
import router from 'router/router'
import App from 'pages/App.vue'
import store from 'store/store'
import 'vuetify/dist/vuetify.min.css'
import { BarChart, WordCloudChart } from 'dr-vue-echarts';

Vue.use(Vuetify)
Vue.use(VueCookies)
Vue.use(UUID)

Vue.component(BarChart.name, BarChart);

new Vue({
    el: '#app',
    vuetify: new Vuetify(),
    store,
    router,
    render: a => a(App)
});