<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-row justify="center">
        <v-col sm="8">
            <v-text-field
                    label="Выбери аудио"
                    @click='pickFile'
                    v-model='audioName'
                    prepend-icon='attach_file'
                    clear-icon="close"
                    @click:clear="clearFile"
                    clearable
                    outlined
                    dense
            >
            </v-text-field>
            <v-card class="d-flex flex-column" :height="100">
                <v-container>
                    <div v-if="this.audio !== null">
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
                <input
                        type="file"
                        style="display: none"
                        ref="audio"
                        accept="audio/*"
                        @change="onFilePicked"
                >
            </v-card>
        </v-col>
    </v-row>
</template>

<script>
    import store from '../store/store'
    import { mapGetters } from 'vuex'

    export default {
        name: 'BuildingAudioForm',
        props: ['valueQuestion'],
        data: () => ({
            dialog: false,
            audioName: 'Выбери аудио...',
            audioUrl: '',
            audio: null,
            audioIcon: 'play_circle_filled',
            rangeTimeline: false,
            timeline: 0,
            maxTime: 0
        }),

        methods: {
            ...mapGetters(['getWindowHeight']),
            playSound() {
                if (this.audio !== null && this.audioIcon === 'play_circle_filled') {
                    this.audio.play()
                    this.audioIcon = 'pause_circle_filled'
                } else if (this.audio !== null && this.audioIcon === 'pause_circle_filled') {
                    this.audio.pause()
                    this.audioIcon = 'play_circle_filled'
                }
            },

            pickFile () {
                this.$refs.audio.click ()
            },

            changeTimeline() {
                this.audio.currentTime = this.timeline
            },

            onFilePicked (e) {
                const files = e.target.files
                if(files[0] !== undefined) {
                    this.audioName = files[0].name
                    if(this.audioName.lastIndexOf('.') <= 0) {
                        return
                    }
                    const readerAudio = new FileReader()
                    readerAudio.readAsDataURL(files[0])
                    readerAudio.addEventListener('load', () => {
                        this.audio = new Audio(readerAudio.result)
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
                    })
                    const readerFile = new FileReader ()
                    readerFile.readAsArrayBuffer(files[0])
                    readerFile.addEventListener('load', () => {
                        let resultBinary = ''
                        const bytes = new Uint8Array(readerFile.result)
                        for (let i = 0; i < bytes.length; i++) {
                            resultBinary += String.fromCharCode(bytes[i])
                        }
                        this.audioFile = window.btoa(resultBinary)
                    })
                } else {
                    this.audioName = ''
                    this.audioFile = ''
                    this.audioUrl = ''
                    this.audio.pause()
                    this.audio = null
                }
            },

            clearFile() {
                this.imageName = ''
                this.audioFile = ''
                this.audioUrl = ''
                this.audio.pause()
                this.audio = null
            },

            destroyed() {
                this.audio.pause()
                this.audio = null
            }
        },

        computed: {
            audioFile: {
                get() {
                    return this.valueQuestion;
                },
                set(val) {
                    this.$emit('input', val);
                }
            }
        }

    }
</script>

<style>
    .v-slider {
        margin-top: 8px !important;
    }
</style>