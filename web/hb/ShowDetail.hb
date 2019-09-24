<div id="ShowDetail" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Edit an Entry</h4>
            </div>
            <div class="modal-body">
                <label for="ShowDetail-title">Event Title</label>
                <input class="form-control" type="text" id="ShowDetail-title" />
                <label for="ShowDetail-message">Details</label>
                <textarea class="form-control" id="ShowDetail-message"></textarea>
                <label for="ShowDetail-created">Event created</label>
                <span id="ShowDetail-created"></span>
                <input type="hidden" id="ShowDetail-detailId" />
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="ShowDetail-likebtn"></button>
                <button type="button" class="btn btn-default" id="ShowDetail-editbtn">Edit</button>
                <button type="button" class="btn btn-default" id="ShowDetail-delbtn">Delete</button>
                <button type="button" class="btn btn-default" id="ShowDetail-closebtn">Close</button>
            </div>
        </div>
    </div>
</div>
