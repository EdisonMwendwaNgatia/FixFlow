package com.example.fixflow

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object FirebaseService {
    private const val adminEmail = "admin@fixflow.com"
    private const val adminPassword = "admin123"
    private const val user1Email = "user1@fixflow.com"
    private const val user1Password = "user123"
    private const val user2Email = "user2@fixflow.com"
    private const val user2Password = "user234"

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    // Authenticate user with hardcoded credentials
    fun loginUser(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        when {
            email == adminEmail && password == adminPassword -> {
                callback(true, null)
            }
            email == user1Email && password == user1Password -> {
                callback(true, null)
            }
            email == user2Email && password == user2Password -> {
                callback(true, null)
            }
            else -> {
                callback(false, "Invalid credentials")
            }
        }
    }

    // Save data to Realtime Database
    fun <T> saveData(path: String, data: T, callback: (Boolean) -> Unit) {
        val databaseReference: DatabaseReference = database.reference.child(path)
        databaseReference.setValue(data)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }

    // Retrieve data from Realtime Database
    fun <T> retrieveData(path: String, clazz: Class<T>, callback: (List<T>?, String?) -> Unit) {
        val databaseReference: DatabaseReference = database.reference.child(path)

        databaseReference.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val dataList = snapshot.children.mapNotNull { it.getValue(clazz) }
                callback(dataList, null)
            } else {
                callback(null, "No data found")
            }
        }.addOnFailureListener { exception ->
            callback(null, exception.message)
        }
    }

    // Add an issue to the Realtime Database
    fun addIssue(caseId: String, issueName: String, callback: (Boolean, String?) -> Unit) {
        val issueRef = database.reference.child("cases").child(caseId).child("issues").push()
        val issueData = hashMapOf("name" to issueName)

        issueRef.setValue(issueData)
            .addOnSuccessListener { callback(true, issueRef.key) }
            .addOnFailureListener { callback(false, null) }
    }

    fun getIssues(caseId: String, callback: (List<Issue>?, String?) -> Unit) {
        val issuesRef = database.reference.child("cases").child(caseId).child("issues")
        issuesRef.get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val issues = snapshot.children.mapNotNull { it.toIssue(it.key) }
                    callback(issues, null)
                } else {
                    callback(emptyList(), "No issues found for this case")
                }
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

    fun getChecklist(caseId: String, issueId: String, callback: (List<String>?, String?) -> Unit) {
        val checklistRef = database.reference
            .child("cases")
            .child(caseId)
            .child("issues")
            .child(issueId)
            .child("checklistSteps")

        checklistRef.get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    // Get all checklist steps as strings (ignore the step IDs)
                    val steps = snapshot.children.mapNotNull { it.getValue(String::class.java) }
                    callback(steps, null)
                } else {
                    callback(emptyList(), "No checklist steps found for this issue.")
                }
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }


    fun addCase(caseName: String, createdBy: String, callback: (Boolean, String?) -> Unit) {
        // Create a reference to the "cases" node and generate a unique key for the new case
        val caseRef = database.reference.child("cases").push()

        // Prepare the case data to be saved
        val caseData = hashMapOf(
            "name" to caseName,
            "createdAt" to System.currentTimeMillis(),
            "createdBy" to createdBy
        )

        // Set the case data to Firebase
        caseRef.setValue(caseData)
            .addOnSuccessListener {
                // Log success and return the unique case ID
                Log.d("AddCase", "Case added successfully with ID: ${caseRef.key}")
                callback(true, caseRef.key) // Successfully added case, return the case ID
            }
            .addOnFailureListener { exception ->
                // Log failure and provide the error message
                Log.e("AddCase", "Failed to add case: ${exception.message}")
                callback(false, null) // Failed to add case, return null as the case ID
            }
    }


    fun updateChecklistStep(
        caseId: String,
        issueId: String,
        checklistStepId: String,
        updatedDescription: String,
        callback: (Boolean) -> Unit
    ) {
        // Reference to the specific checklist step in the Firebase database
        val checklistStepRef = database.reference
            .child("cases")
            .child(caseId)
            .child("issues")
            .child(issueId)
            .child("checklistSteps")
            .child(checklistStepId)

        // Update the value of the checklist step ID
        checklistStepRef.setValue(updatedDescription)
            .addOnSuccessListener {
                callback(true) // Call the callback with success
            }
            .addOnFailureListener {
                callback(false) // Call the callback with failure
            }
    }

    fun getCases(callback: (List<Case>?, String?) -> Unit) {
        val casesRef = database.reference.child("cases")
        casesRef.get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val cases = snapshot.children.mapNotNull { it.toCase(it.key) }
                    callback(cases, null)
                } else {
                    callback(emptyList(), "No cases found")
                }
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

    fun getChecklistStep(caseId: String, issueId: String, checklistStepId: String, callback: (String?, String?) -> Unit) {
        val checklistRef: DatabaseReference = database.reference
            .child("cases")
            .child(caseId)
            .child("issues")
            .child(issueId)
            .child("checklistSteps")
            .child(checklistStepId)

        checklistRef.get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    // Retrieve the step description from the database
                    val checklistStep = snapshot.getValue(String::class.java)
                    callback(checklistStep, null)
                } else {
                    callback(null, "Checklist step not found")
                }
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

    fun addChecklistStep(caseId: String, issueId: String, stepDescription: String, callback: (Boolean) -> Unit) {
        val stepRef = database.reference.child("cases").child(caseId).child("issues").child(issueId).child("checklistSteps").push()
        val stepData = stepDescription

        stepRef.setValue(stepData)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }

    private fun DataSnapshot.toCase(caseId: String?): Case? {
        val name = this.child("name").getValue(String::class.java)
        val createdAt = this.child("createdAt").getValue(Long::class.java) ?: System.currentTimeMillis()
        val createdBy = this.child("createdBy").getValue(String::class.java) ?: ""
        return if (name != null && caseId != null) Case(caseId, name, createdAt, createdBy) else null
    }

    private fun DataSnapshot.toIssue(issueId: String?): Issue? {
        val name = this.child("name").getValue(String::class.java)
        val description = this.child("description").getValue(String::class.java) ?: ""
        val createdAt = this.child("createdAt").getValue(Long::class.java) ?: System.currentTimeMillis()
        val createdBy = this.child("createdBy").getValue(String::class.java) ?: ""
        val checklistSteps = this.child("checklistSteps").children.map { it.getValue(String::class.java) ?: "" } // Correctly map the checklist steps

        return if (name != null && issueId != null) {
            Issue(issueId, name, description, createdAt, createdBy, checklistSteps)
        } else {
            null
        }
    }

}
