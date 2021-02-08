package com.sisada.neversituptodo.etc

import com.google.android.material.textfield.TextInputLayout


class Validator {

    class ValidateShortTest(var inputText: TextInputLayout, var minLength:Int){
        fun validate():Boolean {
            return inputText.editText?.text?.length!! >= minLength
        }
    }
    class ValidateNumberRangeTest(var inputText: TextInputLayout, var intRange:IntRange){
        fun validate():Boolean {
            var number = inputText.editText?.text.toString().toInt()
            return intRange.contains(number)
        }
    }

    private var validationsEmail = mutableListOf<TextInputLayout>()
    private var validationsEmpty = mutableListOf<TextInputLayout>()
    private var validationsShort = mutableListOf<ValidateShortTest>()
    private var validationsNumberRange = mutableListOf<ValidateNumberRangeTest>()

    fun addCheckEmpty(value: TextInputLayout): Validator{
        validationsEmpty.add(value)
        return this
    }

    fun addCheckEmail(value: TextInputLayout): Validator{
        validationsEmail.add(value)
        return this
    }

    fun addCheckTooShort(value: TextInputLayout, length:Int): Validator{
        validationsShort.add(ValidateShortTest(value,length))
        return this
    }

    fun addCheckNumberRange(value: TextInputLayout, intRange:IntRange): Validator{
        validationsNumberRange.add(ValidateNumberRangeTest(value,intRange))
        return this
    }



    private fun validateEmail(): Boolean {
        val check = validationsEmail.filter {
            !android.util.Patterns.EMAIL_ADDRESS.matcher(it.editText?.text!!).matches()
        }

        check.map {
            it.error = "Incorrect format"
        }

        return check.isEmpty()
    }


    private fun validateForNotEmpty(): Boolean {
        val check = validationsEmpty.filter {
            it.editText?.text.isNullOrEmpty()
        }

        check.map {
            it.error = "Field can't be empty"
        }

        return check.isEmpty()
    }

    private fun validateForNotTooShort(): Boolean {

        val check = validationsShort.filter {
            !it.validate()
        }

        check.map {
            it.inputText.error = "Must be at least ${it.minLength} long"
        }

        return check.isEmpty()
    }

    private fun validateForNumberRange(): Boolean {

        val check = validationsNumberRange.filter {
            !it.validate()
        }

        check.map {
            it.inputText.error = "Must be between ${it.intRange.first} - ${it.intRange.last}"
        }

        return check.isEmpty()
    }

    fun result(): Boolean {
        validationsEmpty.map { it.error = null }
        validationsEmail.map { it.error = null }
        validationsShort.map { it.inputText.error = null }
        validationsNumberRange.map { it.inputText.error = null }
        val notEmpty = validateForNotEmpty()
        val notTooShort = validateForNotTooShort()
        val isEmail = validateEmail()
        val isInRange = validateForNumberRange()
        return notEmpty && notTooShort && isEmail && isInRange
    }

}