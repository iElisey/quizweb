import Vue from 'vue'
import VueRouter from 'vue-router'
import CreateView from "../views/CreateView";
import WebQuiz from "../components/WebQuiz";
import GetAllView from "../views/GetAllView";
import AdminView from "../views/AdminView";
import GetView from "../views/GetView";
import LoginView from "../views/LoginView";
import ProfileView from "../views/ProfileView";

Vue.use(VueRouter)


export const router = new VueRouter({
    mode: 'history',
    routes: [

        // {
        //   path: '/login',
        //   name: 'login',
        //   component: LoginView
        // },
        {
            path: '/',
            name: 'getAll',

            component: GetAllView
        },
        {
            path: '/create',
            name: 'create',
            component: CreateView
        },
        {
            path: '/get/',
            name: 'get',

            component: WebQuiz
        },

        {
            path: '/admin',
            name: 'adminBoard',

            component: AdminView
        },
        {
            path: '/get/:id',
            name: 'getId',

            component: GetView
        },
        {
            path: '/profile',
            name: 'profile',

            component: ProfileView
        }
    ]
})
