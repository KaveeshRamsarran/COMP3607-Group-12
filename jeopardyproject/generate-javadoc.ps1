# PowerShell script to generate and optionally publish JavaDoc to GitHub Pages
# Usage: .\generate-javadoc.ps1 [-Publish]

param(
    [switch]$Publish = $false
)

Write-Host "================================" -ForegroundColor Cyan
Write-Host "Jeopardy Game - JavaDoc Generator" -ForegroundColor Cyan
Write-Host "================================" -ForegroundColor Cyan
Write-Host ""

# Generate JavaDoc
Write-Host "Generating JavaDoc documentation..." -ForegroundColor Yellow
mvn javadoc:javadoc

if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: JavaDoc generation failed!" -ForegroundColor Red
    exit 1
}

Write-Host "SUCCESS: JavaDoc generated successfully!" -ForegroundColor Green
Write-Host "Location: target/site/apidocs" -ForegroundColor Green
Write-Host ""

# Copy to docs folder for GitHub Pages
$docsDir = ".\docs"
$sourceDir = ".\target\site\apidocs"

if (Test-Path $sourceDir) {
    Write-Host "Copying JavaDoc to docs/ folder for GitHub Pages..." -ForegroundColor Yellow
    
    # Create docs directory if it doesn't exist
    if (-not (Test-Path $docsDir)) {
        New-Item -ItemType Directory -Path $docsDir | Out-Null
    }
    
    # Copy all JavaDoc files
    Copy-Item -Path "$sourceDir\*" -Destination $docsDir -Recurse -Force
    
    Write-Host "SUCCESS: JavaDoc copied to docs/ folder" -ForegroundColor Green
    Write-Host ""
}

# Create .nojekyll file for GitHub Pages
$nojekyll = Join-Path $docsDir ".nojekyll"
if (-not (Test-Path $nojekyll)) {
    New-Item -ItemType File -Path $nojekyll -Force | Out-Null
    Write-Host "Created .nojekyll file for GitHub Pages" -ForegroundColor Green
}

# Open the JavaDoc in browser
Write-Host "Opening JavaDoc in browser..." -ForegroundColor Yellow
$indexPath = Join-Path $docsDir "index.html"
if (Test-Path $indexPath) {
    Start-Process $indexPath
}

Write-Host ""
Write-Host "================================" -ForegroundColor Cyan
Write-Host "Next Steps:" -ForegroundColor Cyan
Write-Host "================================" -ForegroundColor Cyan
Write-Host "1. Review the generated documentation" -ForegroundColor White
Write-Host "2. Commit and push to GitHub:" -ForegroundColor White
Write-Host "   git add docs/" -ForegroundColor Gray
Write-Host "   git commit -m 'Add JavaDoc API documentation'" -ForegroundColor Gray
Write-Host "   git push origin main" -ForegroundColor Gray
Write-Host ""
Write-Host "3. Enable GitHub Pages:" -ForegroundColor White
Write-Host "   - Go to repository Settings > Pages" -ForegroundColor Gray
Write-Host "   - Source: Deploy from a branch" -ForegroundColor Gray
Write-Host "   - Branch: main, Folder: /docs" -ForegroundColor Gray
Write-Host "   - Save and wait for deployment" -ForegroundColor Gray
Write-Host ""
Write-Host "4. Your API docs will be available at:" -ForegroundColor White
Write-Host "   https://KaveeshRamsarran.github.io/COMP3607-Group-12/" -ForegroundColor Green
Write-Host ""

# Optional: Auto-commit and push
if ($Publish) {
    Write-Host "Publishing to GitHub..." -ForegroundColor Yellow
    git add docs/
    git commit -m "Update JavaDoc API documentation"
    git push origin main
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "SUCCESS: Published to GitHub!" -ForegroundColor Green
    } else {
        Write-Host "WARNING: Failed to push to GitHub. Please push manually." -ForegroundColor Yellow
    }
}
