<template>
    <v-app>
        <v-app-bar app>
            <v-toolbar-title>Finally</v-toolbar-title>
            <v-btn @click="showProfile" text :disabled="$route.path === '/profile'">Profile</v-btn>
            <v-spacer></v-spacer>
            <v-btn icon href="#">
                <v-icon>exit_to_app</v-icon>
            </v-btn>
        </v-app-bar>
        <v-content>
            <router-view></router-view>
        </v-content>
    </v-app>
</template>

<script>
    import { mapState, mapMutations } from 'vuex'
    import { addHandler } from 'util/ws'

    export default {
        methods: {
            ...mapMutations(['addMessageMutation', 'updateMessageMutation', 'removeMessageMutation']),
            showProfile() {
                this.$router.push('/profile')
            }
        },
        created() {
            addHandler(data => {
                if (data.objectType === 'MESSAGE') {
                    switch(data.eventType) {
                        case 'CREATE':
                            this.addMessageMutation(data.body)
                            break
                        case 'UPDATE':
                            this.updateMessageMutation(data.body)
                            break
                        case 'REMOVE':
                            this.removeMessageMutation(data.body)
                            break
                        default:
                            console.error('Xz che za Event "${data.eventType}"')
                    }
                } else {
                    console.error('Xz che za Object "${data.objectType}"')
                }
            })
        }
    }
</script>

<style>

</style>