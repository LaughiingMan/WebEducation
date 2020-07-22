<template>
    <v-row justify="center">
        <v-content>
            <transition>
                <router-view name="authorization"></router-view>
            </transition>
        </v-content>
    </v-row>
</template>

<script>
    import store from '../store/store'

    export default {
        name: "ActivateCodeForm",
        beforeRouteEnter(to, from, next) {
            const path = to.fullPath.toString().split("/")
            const code = path[path.length - 1]

            store.dispatch('checkActivatedCodeAction', code)

            setTimeout(function(){
                next()
            }, 100)
        },
    }
</script>

<style>

</style>