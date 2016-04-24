"# Zoom_Show_Long_image" 
    
    4 demos

    public void showAsCenterInSide(View view){startActivity(new Intent(this,CenterInsideActivity.class));}

    public void showAsPages(View view){
        startActivity(new Intent(this,PagesActivity.class));
    }

    public void showZoom(View view){
        startActivity(new Intent(this,ZoomPhotoActivity.class));
    }

    public void showZoomWithfresco(View view){
        Intent intent = new Intent(this,ZoomPhotoActivity.class);
        intent.putExtra("type","fresco");
        startActivity(intent);
    }
