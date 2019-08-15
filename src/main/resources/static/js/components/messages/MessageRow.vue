<template>
    <v-card class="my-2">
        <v-card-text>
            <i>{{ message.id }}</i>
            {{ message.text }}
        </v-card-text>
        <v-card-actions>
            <v-btn @click="edit" text small rounded>Edit</v-btn>
            <v-btn icon @click="remove" small>
                <v-icon>delete</v-icon>
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    import { mapActions } from 'vuex'

    export default {
        props: ['message', 'editMessage'],
        methods: {
            ...mapActions(['removeMessageAction']),
            edit() {
                this.editMessage(this.message)
            },
            remove() {
                this.removeMessageAction(this.message)
            }
        }
    }
</script>

<style>

</style>