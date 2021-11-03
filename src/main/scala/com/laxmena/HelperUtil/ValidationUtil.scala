package com.laxmena.HelperUtil

/**
 * ValidationUtil
 *
 * Utility class for validating input data.
 *
 * @author laxmena
 */
class ValidationUtil
object ValidationUtil {

  /**
   * Validate if the input string is empty.
   * @param value Input string
   * @param regex Regex to validate
   * @return Boolean returns true if the input string matches the regex pattern.
   */
  def validate(value: String, regex: String): Boolean = {
    value.matches(regex)
  }

  /**
   * Validate if the date is in valid format(YYYY-DD-MM).
   *
   * @param date Input date string
   * @return Boolean returns true if the date format is valid.
   */
  def validateDate(date: String): Boolean = {
    // Validate DateFormat: YYYY-MM-DD
    val dateFormat = "^\\d{4}-\\d{2}-\\d{2}$"
    validate(date, dateFormat)
  }

  /**
   * Validate if time is in valid format(HH:MM:SS).
   *
   * @param time Input time string
   * @return Boolean returns true if the time format is valid.
   */
  def validateTime(time: String): Boolean = {
    // Validate TimeFormat: HH:MM:SS
    val timeFormat = "^\\d{2}:\\d{2}:\\d{2}$"
    validate(time, timeFormat)
  }

  /**
   * Validate if timeWindow is valid (Int).
   *
   * @param value Input timeWindow string
   * @return Boolean returns true if the value is valid Integer.
   */
  def validateWindow(value: String): Boolean = {
    // Validate Window: Integer
    val integerFormat = "^\\d+$"
    validate(value, integerFormat)
  }

}
