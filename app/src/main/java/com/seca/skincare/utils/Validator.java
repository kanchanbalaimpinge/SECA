package com.seca.skincare.utils;

import static android.graphics.Color.*;

import android.content.Context;
import android.os.Build;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;

public class Validator {


    public static  boolean isValidateEmail(Context context, EditText editText, TextView textView, String message) {

        if (TextUtils.isEmpty(editText.getText().toString()) ) {

           // textInputLayout.setError(message);
            SpannableStringBuilder builder = new SpannableStringBuilder();

            String error = textView.getTag().toString();
            builder.append(textView.getText().toString());
            SpannableString whiteSpannable= new SpannableString(error);
            whiteSpannable.setSpan(new ForegroundColorSpan(RED), 0, error.length(), 0);
            builder.append(whiteSpannable);

            textView.setText(builder, TextView.BufferType.SPANNABLE);



            return false;

        } else if (!editText.getText().toString().trim().matches(Patterns.EMAIL_ADDRESS.pattern())) {
            SpannableStringBuilder builder = new SpannableStringBuilder();

            String error = textView.getTag().toString();
            builder.append(textView.getText().toString());
            SpannableString whiteSpannable= new SpannableString(error);
            whiteSpannable.setSpan(new ForegroundColorSpan(RED), 0, error.length(), 0);
            builder.append(whiteSpannable);

            textView.setText(builder, TextView.BufferType.SPANNABLE);


            //  textInputLayout.setError(message);
            return false;

        }

   //     textInputLayout.setErrorEnabled(false);

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static  boolean isValidateText(Context context, EditText editText, AppCompatTextView textView, String message, int validateLength) {


        if (TextUtils.isEmpty(editText.getText().toString()) ) {

            SpannableStringBuilder builder = new SpannableStringBuilder();

            String error = message;
            builder.append(textView.getTag().toString());
            SpannableString whiteSpannable= new SpannableString(error);
            whiteSpannable.setSpan(new ForegroundColorSpan(RED), 0, error.length(), 0);

            builder.append(whiteSpannable);

            textView.setText(builder, TextView.BufferType.SPANNABLE);

            return false;

        } else if (editText.getText().toString().length()<validateLength) {

            SpannableStringBuilder builder = new SpannableStringBuilder();

            String error = message;
            builder.append(textView.getTag().toString());
            SpannableString whiteSpannable= new SpannableString(error);
            whiteSpannable.setSpan(new ForegroundColorSpan(RED), 0, error.length(), 0);

            builder.append(whiteSpannable);

            textView.setText(builder, TextView.BufferType.SPANNABLE);

            return false;

        }

        String error = textView.getTag().toString();
        textView.setText(error);
        return true;

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean isValidatepassword(Context context, EditText editText,  EditText editText1, AppCompatTextView textView,String message, int validateLength) {

        try {

            if (!isValidateText(context, editText, textView, message, validateLength)) {
                SpannableStringBuilder builder = new SpannableStringBuilder();

                String error = message;
                builder.append(textView.getTag().toString());
                SpannableString whiteSpannable = new SpannableString(error);
                whiteSpannable.setSpan(new ForegroundColorSpan(RED), 0, error.length(), 0);
                builder.append(whiteSpannable);

                textView.setText(builder, TextView.BufferType.SPANNABLE);

                return false;

            } else if (!isValidateText(context, editText, textView, message, validateLength)) {


                SpannableStringBuilder builder = new SpannableStringBuilder();

                String error = message;
                builder.append(textView.getTag().toString());
                SpannableString whiteSpannable = new SpannableString(error);
                whiteSpannable.setSpan(new ForegroundColorSpan(RED), 0, error.length(), 0);
                builder.append(whiteSpannable);

                textView.setText(builder, TextView.BufferType.SPANNABLE);

                return false;

            } else if (!editText.getText().toString().trim().matches(editText1.getText().toString().trim())) {

                SpannableStringBuilder builder = new SpannableStringBuilder();

                String error = message;
                builder.append(textView.getText().toString());
                SpannableString whiteSpannable = new SpannableString(error);
                whiteSpannable.setSpan(new ForegroundColorSpan(RED), 0, error.length(), 0);
                builder.append(whiteSpannable);

                textView.setText(builder, TextView.BufferType.SPANNABLE);

                return false;

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        String error = textView.getTag().toString();
        textView.setText(error);

        return true;
    }
}
