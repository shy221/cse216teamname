/// <reference path="ts/EditEntryForm.ts"/>
/// <reference path="ts/NewEntryForm.ts"/>
/// <reference path="ts/ElementList.ts"/>
/// <reference path="ts/Navbar.ts"/>
/// <reference path="ts/ShowDetail.ts"/>
/// <reference path="ts/Login.ts"/>
/// <reference path="ts/UserProfile.ts"/>
/// <reference path="ts/ShowComments.ts"/>
/// <reference path="ts/EditUserProfile.ts"/>

// Prevent compiler errors when using jQuery.  "$" will be given a type of 
// "any", so that we can use it anywhere, and assume it has any fields or
// methods, without the compiler producing an error.
let $: any;

// Prevent compiler errors when using Handlebars
let Handlebars: any;

// a global for the EditEntryForm of the program.  See newEntryForm for 
// explanation
let editEntryForm: EditEntryForm;
var uemail: String;
var ukey: String;
var loginState: boolean = false;
var uid: number;

// Run some configuration code when the web page loads
$(document).ready(function () {
    Navbar.refresh();
    NewEntryForm.refresh();
    ElementList.refresh();
    EditEntryForm.refresh();
    ShowDetail.refresh();
    Login.refresh();
    UserProfile.refresh();
    ShowComments.refresh();
    EditUserProfile.refresh();
    /*gapi.load('auth2', function() {
        // Initialize `auth2`
        gapi.auth2.init().then(function(auth2) {
            
          // If the user is already signed in
          if (auth2.isSignedIn.get()) {
            var googleUser = auth2.currentUser.get();
            
            // Change user's profile information
            changeProfile(googleUser);
          }
        });
      });
    // Create the object that controls the "Edit Entry" form
    // set up initial UI state
    $("#editElement").hide();*/
});