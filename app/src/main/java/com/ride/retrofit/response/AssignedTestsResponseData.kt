package com.ride.retrofit.response

import java.io.Serializable


class AssignedTestsResponseData : Serializable{
    var status: String? = null
    var message: String? = null
    var statusCode = 0
    var data: Data? = null

    inner class Data {
        var completedSamples: List<CompletedSamples>? =
            null
        var assignedSamples: List<AssignedSamples>? =
            null


        inner class AssignedSamples: Serializable{
            var appointment: Appointment? =
                null
            var feedBack: FeedBack? = null
            var comments: String? = null
            var paymentDone = false
            var patient: Patient? = null
            var lab: Lab? = null
            var tests: List<Tests>? =
                null
            var address: Address? = null
            var userId: String? = null

            inner class Appointment : Serializable{
                 var _id: String? = null
                var uniqueAppointmentId: String? = null
                var appointmentDate: String? = null
                var appointmentTime: String? = null
                var appointmentSession: String? = null
                var appointmentStatus: String? = null
                var isAppointmentConfirmed = false
                var bookingTime: String? = null

            }

            inner class FeedBack : Serializable{
                var comments: String? = null
                var rating = 0

            }

            inner class Patient : Serializable{
                private var _id: String? = null
                var name: String? = null
                var email: String? = null
                var mobile: String? = null
                var gender: String? = null
                var dob: String? = null
                var age = 0
                fun set_id(_id: String?) {
                    this._id = _id
                }

                fun get_id(): String? {
                    return _id
                }

            }

            inner class Lab : Serializable{
                private var _id: String? = null
                var name: String? = null
                fun set_id(_id: String?) {
                    this._id = _id
                }

                fun get_id(): String? {
                    return _id
                }

            }

            inner class Tests : Serializable{
                private var _id: String? = null
                var testName: String? = null
                var isService = false
                var testReport: String? = null
                var labId: String? = null
                var labName: String? = null
                fun set_id(_id: String?) {
                    this._id = _id
                }

                fun get_id(): String? {
                    return _id
                }

            }

            inner class Address : Serializable{
                var address1: String? = null
                var address2: String? = null
                var city: String? = null
                var pinCode: String? = null
                var state: String? = null
                var addressLabel: String? = null
                var longitude: String? = null
                var latitude: String? = null

            }
        }


          inner class CompletedSamples : Serializable{
            var appointment: Appointment? =
                null
            var feedBack: FeedBack? = null
            var comments: String? = null
            var paymentDone = false
            var patient: Patient? = null
            var lab: Lab? = null
            var tests: List<Tests>? =
                null
            var address: Address? = null
            var userId: String? = null

            inner class Appointment : Serializable{
                private var _id: String? = null
                var uniqueAppointmentId: String? = null
                var appointmentDate: String? = null
                var appointmentTime: String? = null
                var appointmentSession: String? = null
                var appointmentStatus: String? = null
                var isAppointmentConfirmed = false
                var bookingTime: String? = null
                fun set_id(_id: String?) {
                    this._id = _id
                }

                fun get_id(): String? {
                    return _id
                }

            }

            inner class FeedBack: Serializable
            inner class Patient : Serializable{
                private var _id: String? = null
                var name: String? = null
                var email: String? = null
                var mobile: String? = null
                var gender: String? = null
                var dob: String? = null
                var age = 0
                fun set_id(_id: String?) {
                    this._id = _id
                }

                fun get_id(): String? {
                    return _id
                }

            }

            inner class Lab : Serializable{
                private var _id: String? = null
                var name: String? = null
                fun set_id(_id: String?) {
                    this._id = _id
                }

                fun get_id(): String? {
                    return _id
                }

            }

            inner class Tests : Serializable{
                private var _id: String? = null
                var testName: String? = null
                var isService = false
                var testReport: String? = null
                var labId: String? = null
                var labName: String? = null
                fun set_id(_id: String?) {
                    this._id = _id
                }

                fun get_id(): String? {
                    return _id
                }

            }

            inner class Address : Serializable{
                var address1: String? = null
                var address2: String? = null
                var city: String? = null
                var pinCode: String? = null
                var state: String? = null
                var addressLabel: String? = null
                var longitude: String? = null
                var latitude: String? = null

            }


        }
    }

}

