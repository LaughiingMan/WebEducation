<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-card class="d-flex flex-column" :height="100">
        <v-container>
            <div v-if="inputVal">
                <v-row>
                    <v-col>
                        <v-slider
                                class="mt-1 mr-10"
                                v-if="!rangeTimeline"
                                v-model="timeline"
                                :value="timeline"
                                :min="0"
                                :max="maxTime"
                                @click="changeTimeline()"
                                @end="changeTimeline()"
                        >
                            <template v-slot:prepend>
                                <v-btn class="ml-5 primary white--text" icon @click.prevent="playSound()">
                                    <v-icon>{{audioIcon}}</v-icon>
                                </v-btn>
                            </template>
                        </v-slider>
                        <v-range-slider
                                v-else
                                :min="0"
                                :max="maxTime"
                        ></v-range-slider>
                    </v-col>
                </v-row>
            </div>
        </v-container>
    </v-card>
</template>

<script>
    import store from '../store/store'
    import { mapGetters } from 'vuex'

    export default {
        name: 'AudioTestComponent',
        props: ['valueQuestion'],
        data: () => ({
            dialog: false,
            audioName: '',
            audioUrl: '',
            audio: null,
            audioIcon: 'play_circle_filled',
            rangeTimeline: false,
            timeline: 0,
            maxTime: 0
        }),
        methods: {
            prepareAudio() {
                this.audio = new Audio(this.valueQuestion)
                this.audio.addEventListener('canplaythrough', () => {
                    this.maxTime = this.audio.duration
                    this.audio.addEventListener('timeupdate', () => {
                        this.timeline = this.audio.currentTime
                        if (this.timeline === this.maxTime) {
                            this.audioIcon = 'play_circle_filled'
                            this.timeline = 0
                        }
                    })
                });
            },
            playSound() {
                if (this.audio !== null && this.audioIcon === 'play_circle_filled') {
                    this.audio.play()
                    this.audioIcon = 'pause_circle_filled'
                } else if (this.audio !== null && this.audioIcon === 'pause_circle_filled') {
                    this.audio.pause()
                    this.audioIcon = 'play_circle_filled'
                }
            },
            changeTimeline() {
                this.audio.currentTime = this.timeline
            },
            destroyed() {
                this.audio.pause()
                this.audio = null
            }
        },
        computed: {
            inputVal: {
                get() {
                    const bstr = window.atob(this.valueQuestion)
                    let n = bstr.length;
                    const u8arr = new Uint8Array(n)
                    while (n--) {
                        u8arr[n] = bstr.charCodeAt(n)
                    }
                    this.valueQuestion = URL.createObjectURL(new Blob([u8arr.buffer], {type: 'audio/mpeg'}))
                    this.prepareAudio()
                    return this.valueQuestion
                },
                set(val) {
                    this.$emit('input', val)
                }
            }
        }
    }
</script>

<style>

</style>