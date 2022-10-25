import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import {router} from "./router";
import Vuex from "vuex";
import store from "./store/index";

Vue.use(Vuex);

Vue.config.productionTip = false

var app = new Vue({
    vuetify,
    router,
    store,
    el: '#app',
    render: h => h(App),
})
