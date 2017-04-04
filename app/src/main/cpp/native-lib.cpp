#include <jni.h>
#include <string>
JNIEXPORT void JNICALL
Java_org_sil_palaso_Graphite_loadGraphite(JNIEnv *env, jclass type) {

    // TODO

}
JNIEXPORT jobject JNICALL
Java_org_sil_palaso_Graphite_addFontResource(JNIEnv *env, jclass type, jobject assets,
                                             jstring asset_name_, jstring font_name_, jint rtl,
                                             jstring lang_, jstring feats_) {
    const char *asset_name = env->GetStringUTFChars(asset_name_, 0);
    const char *font_name = env->GetStringUTFChars(font_name_, 0);
    const char *lang = env->GetStringUTFChars(lang_, 0);
    const char *feats = env->GetStringUTFChars(feats_, 0);

    // TODO

    env->ReleaseStringUTFChars(asset_name_, asset_name);
    env->ReleaseStringUTFChars(font_name_, font_name);
    env->ReleaseStringUTFChars(lang_, lang);
    env->ReleaseStringUTFChars(feats_, feats);
}
