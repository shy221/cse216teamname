/**
 * The Navbar Singleton is the navigation bar at the top of the page.  Through 
 * its HTML, it is designed so that clicking the "brand" part will refresh the
 * page.  Apart from that, it has an "add" button, which forwards to 
 * NewEntryForm
 */
class Navbar {
    /**
     * Track if the Singleton has been initialized
     */
    private static isInit = false;

    /**
     * The name of the DOM entry associated with Navbar
     */
    private static readonly NAME = "Navbar";

    /**
     * Initialize the Navbar Singleton by creating its element in the DOM and
     * configuring its button.  This needs to be called from any public static
     * method, to ensure that the Singleton is initialized before use.
     */
    private static init() {
        if (!Navbar.isInit) {
            $("body").prepend(Handlebars.templates[Navbar.NAME + ".hb"]());
            $("#"+Navbar.NAME+"-add").click(NewEntryForm.show);
            //$("#"+Navbar.NAME+"-login").click(Login.show);
            $("#"+Navbar.NAME+"-Account").click(UserProfile.get);
            $("#"+Navbar.NAME+"-login").click(Navbar.login);
            Navbar.isInit = true;
        }
    }
    public static logout() {
        var auth2 = gapi.auth2.getAuthInstance();
        auth2.signOut();
        ukey = "";
        idToken = "";
        window.location.replace("https://arcane-refuge-67249.herokuapp.com/");
        /*$.ajax({
            type: "POST",
            //not sure abour url
            url: "/logout",
            dataType: "json",
            data: JSON.stringify({"id_token": idToken}),
            success: Navbar.init
        });*/
    }
    public static login() {

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
        });
      });
    }

    /**
     * Refresh() doesn't really have much meaning for the navbar, but we'd 
     * rather not have anyone call init(), so we'll have this as a stub that
     * can be called during front-end initialization to ensure the navbar
     * is configured.
     */
    public static refresh() {
        Navbar.init();
    }
}