import Vue from 'vue'
import VueRouter from 'vue-router'
import Profile from 'pages/Profile.vue'
import ActivateCodeForm from 'pages/ActivateCodeForm.vue'
import UsersForm from 'pages/UsersForm.vue'
import AuthorizationForm from 'pages/AuthorizationForm.vue'
import TestForm from 'pages/TestForm.vue'
import BuildForm from 'pages/BuildForm.vue'
import StartTestForm from 'pages/StartTestForm.vue'
import StatisticsForm from 'pages/StatisticsForm.vue'
import store from '../store/store'

Vue.use(VueRouter)

const routes = [
    { path: '/profile', components: { profile: Profile }, meta: { title: 'Профиль' },
    children: [
        {
            path: 'users',
            components: { users: UsersForm },
            meta: {
                title: 'Пользователи'
            }
        },
        {
            path: 'statistics',
            components: { statistics: StatisticsForm },
            meta: {
                title: 'Статистика'
            }
        },
        {
            path: 'test',
            components: { test: TestForm },
            meta: {
                title: 'Тесты'
            }
        },
        {
            path: 'testing/:id',
            components: { testing: StartTestForm },
            meta: {
                title: 'Тестирование'
            }
        },
        {
            path: 'build',
            components: { test: BuildForm },
            meta: {
                title: 'Конструктор'
            }
        }
    ]},
    { path: '/activate/*', components: { profile: ActivateCodeForm }, meta: { title: 'Активация' }},
    { path: '/authorization', components: { authorization: AuthorizationForm }, meta: { title: 'Авторизация' }},
]

const router = new VueRouter({
    mode: 'history',
    routes
})

router.beforeEach((to, from, next) => {
    document.title = to.path === '/' ? 'Главное меню' : to.meta.title
    if (to.path.includes('/activate/')) {
        next()
    }
    if (to.path !== '/authorization' && window.$cookies.get('jwt.token') === null) {
        next('/authorization')
    }
    if (to.path === '/authorization' && window.$cookies.get('jwt.token') !== null) {
        next('/')
    }
    if (to.path === '/profile/build' && window.$cookies.get('roles').includes('USER')) {
        next('/profile/users')
    }
    store.dispatch('findAuthUserAction')
    store.dispatch('findUsersAction')
    store.dispatch('findTestsAction')
    store.dispatch('findAllStatisticsAction')
    next()
})

export default router