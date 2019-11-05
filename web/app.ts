/// <reference path="ts/EditEntryForm.ts"/>
/// <reference path="ts/NewEntryForm.ts"/>
/// <reference path="ts/ElementList.ts"/>
/// <reference path="ts/Navbar.ts"/>
/// <reference path="ts/ShowDetail.ts"/>
///// <reference path="ts/Login.ts"/>
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
var signedin: boolean = false;
var idToken : string;


var changeProfile = function(googleUser: gapi.auth2.GoogleUser) {
  if (googleUser) {
    var profile = googleUser.getBasicProfile();

    console.log('ID: ' + profile.getId()); 
    console.log('Name: ' + profile.getName());
    console.log('Email: ' + profile.getEmail()); 

  } else {

  }
};
var onSubmitResponse = function(data: any) {
  if (data.mStatus === "ok") {
      console.log('backend ok');
      loginState = true;
      uid = data.mData.uId;
      ukey = data.mData.sessionKey;
      uemail = data.mData.uEmail;
      Navbar.refresh();
      NewEntryForm.refresh();
      ElementList.refresh();
      EditEntryForm.refresh();
      ShowDetail.refresh();
  }
  // Handle explicit errors with a detailed popup pwd
  else if (data.mStatus === "error") {
      window.alert("The server replied with an error:\n" + data.mpwd);
  }
  // Handle other errors with a less-detailed popup pwd
  else {
      window.alert("Unspecified error");
  }
}


// Run some configuration code when the web page loads
$(document).ready(function () {
    Navbar.refresh();
    NewEntryForm.refresh();
    ElementList.refresh();
    EditEntryForm.refresh();
    ShowDetail.refresh();
    //Login.refresh();
    UserProfile.refresh();
    ShowComments.refresh();
    EditUserProfile.refresh();
    var google : string = "https://accounts.google.com/o/oauth2/v2/auth?redirect_uri=https%3A%2F%2Farcane-refuge-67249.herokuapp.com&response_type=code&client_id=689219964832-6m703l22ir6jh9ra1m1lhrgg12bv7olt.apps.googleusercontent.com&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&include_granted_scopes=true";
    
    var home : string = "https://arcane-refuge-67249.herokuapp.com/"
    gapi.load('auth2', function() {
        // Initialize `auth2`
        gapi.auth2.init({
          client_id: '689219964832-6m703l22ir6jh9ra1m1lhrgg12bv7olt.apps.googleusercontent.com'
        }).then(function(auth2) {
          // If the user is already signed in
          if (auth2.isSignedIn.get()) {
            console.log('signed in');
            var googleUser = auth2.currentUser.get();
            // Change user's profile information
            changeProfile(googleUser);
            if (googleUser.hasGrantedScopes("https://www.googleapis.com/auth/userinfo.email")) {
              console.log('granted');
              idToken = googleUser.getAuthResponse().id_token;
              console.log('idToken'+idToken);
              if (!idToken) {
                throw 'Authentication failed.';
              }
              $.ajax({
                type: "POST",
                //not sure abour url
                url: "/login",
                dataType: "json",
                data: JSON.stringify({"id_token": idToken}),
                success: onSubmitResponse
            });
            }
            else {
              console.log('else');
              // Ask the user for a permission.
              // This is for client side API call
              googleUser.grant({
                scope: "https://www.googleapis.com/auth/userinfo.email"
              }).then(function() {
                // Make API call
                console.log('googleUser.grant');
                var idToken = googleUser.getAuthResponse().id_token;
                console.log('idToken'+idToken);
                if (!idToken) {
                  throw 'Authentication failed.';
                }
                //authenticateWithServer

              });
              
            }
          }
          else{
            console.log('signing in');
            //window.location.replace(google);
            auth2.signIn();
            var googleUser = auth2.currentUser.get();
            // Change user's profile information
            changeProfile(googleUser);
              if (googleUser.hasGrantedScopes("https://www.googleapis.com/auth/userinfo.email")) {
              console.log('granted');
              idToken = googleUser.getAuthResponse().id_token;
              console.log('idToken'+idToken);
              if (!idToken) {
                throw 'Authentication failed.';
              }
              $.ajax({
                type: "POST",
                //not sure abour url
                url: "/login",
                dataType: "json",
                data: JSON.stringify({"id_token": idToken}),
                success: onSubmitResponse
              });
              }
            else {
              console.log('else');
              // Ask the user for a permission.
              // This is for client side API call
              googleUser.grant({
                scope: "https://www.googleapis.com/auth/userinfo.email"
              }).then(function() {
                // Make API call
                console.log('googleUser.grant');
                var idToken = googleUser.getAuthResponse().id_token;
                console.log('idToken'+idToken);
                if (!idToken) {
                  throw 'Authentication failed.';
                }

              });
            }
          }
                     
            //window.location.replace(google);
        });
      });
    $("#editElement").hide();
});