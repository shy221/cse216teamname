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
var signedin: boolean = false;

var changeProfile = function(googleUser: gapi.auth2.GoogleUser) {
  // See if `GoogleUser` object is obtained
  // If not, the user is signed out
  if (googleUser) {
    // Get `BasicProfile` object
    var profile = googleUser.getBasicProfile();
    // Get user's basic profile information
    /*profile = {
      name: profile.getName(),
      email: profile.getEmail(),
      imageUrl: profile.getImageUrl()
    };*/
    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Email: ' + profile.getEmail()); 
    /*toast('show-toast', {
      text: "You're signed in."
    });*/
    //$.dialog.close();
  } else {
    // Remove profile information
    // Polymer will take care of the rest
    
    //profile = null;
    
    /*fire('show-toast', {
      text: "You're signed out."
    });*/
  }
};

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
            if (googleUser.hasGrantedScopes("https://mail.google.com/")) {
              console.log('granted');
            }
          }
          else{
            console.log('signing in');
            //window.location.replace(google);
            auth2.signIn()
              .then(changeProfile);
          };
                     
            //window.location.replace(google);
        });
      });

      /*if(window.location.href != home && window.location.href != google){
        signedin == true;
      }
      if(signedin == false){
        window.location.replace(google);
      }
      else{
        console.log(window.location.href);
      }
      */
      
      /*var authCode : string = window.location.href;
      if(window.location.href != google && window.location.href != home ){
        window.location.replace(google);
        console.log(window.location.href);
        window.location.replace("https://arcane-refuge-67249.herokuapp.com/");
      }
      else if(window.location.href != google){
        window.location.replace(google);
        console.log('g');
      }

      */
      //window.location.replace(window.location.href);
    // Create the object that controls the "Edit Entry" form
    // set up initial UI state
    $("#editElement").hide();
});