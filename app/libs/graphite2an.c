#include <jni.h>

JNIEXPORT void JNICALL
Java_org_sil_palaso_Graphite_loadGraphite(JNIEnv *env, jclass type) {

    // TODO

}

JNIEXPORT jobject JNICALL
Java_org_sil_palaso_Graphite_addFontResource(JNIEnv *env, jclass type, jobject assets,
                                             jstring asset_name_, jstring font_name_, jint rtl,
                                             jstring lang_, jstring feats_) {
    const char *asset_name = (*env)->GetStringUTFChars(env, asset_name_, 0);
    const char *font_name = (*env)->GetStringUTFChars(env, font_name_, 0);
    const char *lang = (*env)->GetStringUTFChars(env, lang_, 0);
    const char *feats = (*env)->GetStringUTFChars(env, feats_, 0);

    // TODO

    (*env)->ReleaseStringUTFChars(env, asset_name_, asset_name);
    (*env)->ReleaseStringUTFChars(env, font_name_, font_name);
    (*env)->ReleaseStringUTFChars(env, lang_, lang);
    (*env)->ReleaseStringUTFChars(env, feats_, feats);
}