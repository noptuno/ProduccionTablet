package com.codekolih.producciontablet.aciones;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public abstract class OcultarTeclado extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Agrega el Listener al ConstraintLayout de la actividad
    protected void addKeyboardHideListener(ConstraintLayout constraintLayout) {
        constraintLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // Ocultar el teclado al tocar la pantalla
                hideKeyboard(view);
                return false;
            }
        });
    }

    // Método para ocultar el teclado
    protected void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected void configureEditText(final EditText editText) {
        final boolean[] allowZero = {false};
        final boolean[] isUpdating = {false};
        editText.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                editText.selectAll();
            } else {
                if (!isUpdating[0]) {
                    String text = editText.getText().toString().trim();
                    if (!text.isEmpty()) {
                        int num = Integer.parseInt(text);
                        editText.setText(String.valueOf(num));
                    }
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isUpdating[0]) {
                    String text = s.toString().trim();
                    if (!text.isEmpty()) {
                        if (text.startsWith(".")) {
                            allowZero[0] = true;
                        } else {
                            allowZero[0] = false;
                        }
                        int num = Integer.parseInt(text);
                        isUpdating[0] = true;
                        editText.setText(String.valueOf(num));
                        editText.setSelection(editText.getText().length());
                        isUpdating[0] = false;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
}
