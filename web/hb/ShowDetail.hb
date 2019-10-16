<div id="ShowDetail" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" id="ShowDetail-title"></h3>
            </div>
            <div class="modal-body">
                <div class="user-face">
                    <img class="user-head" src="//static.hdslb.com/images/member/noface.gif" style="width:10%">
                    <h5 class="user-name" id="ShowDetail-username"></h5>
                </div>
                <button type="button" class="btn btn-default" id="ShowDetail-userprofilebtn">user</button>
                <label for="ShowDetail-message">Details</label>
                <textarea class="form-control" id="ShowDetail-message"></textarea>
                <label for="ShowDetail-created">Event created</label>
                <span id="ShowDetail-created"></span>
                <input type="hidden" id="ShowDetail-detailId" />
                <input type="hidden" id="ShowDetail-detailPostUid" />
                <label for="ShowDetail-postcomments">Comments</label>
                <input class="form-control" type="text" id="ShowDetail-postcomments"></textarea>
                
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="ShowDetail-commentsbtn">Leave Comments</button>
                <button type="button" class="btn btn-default" id="ShowDetail-scommentsbtn">Show Comments</button>
                <button type="button" class="btn btn-default" id="ShowDetail-likebtn"></button>
                <button type="button" class="btn btn-default" id="ShowDetail-editbtn">Edit</button>
                <button type="button" class="btn btn-default" id="ShowDetail-delbtn">Delete</button>
                <button type="button" class="btn btn-default" id="ShowDetail-closebtn">Close</button>
            </div>
        </div>
    </div>
</div>
