$baseUrl = "http://localhost:8080"

# --- Login ---
$loginBody = '{"username":"admin","password":"Admin123!"}'
$session = New-Object Microsoft.PowerShell.Commands.WebRequestSession
Invoke-RestMethod -Uri "$baseUrl/api/auth/login" -Method Post -Body $loginBody -ContentType "application/json" -WebSession $session | Out-Null
Write-Host "Login OK`n"

function Medir-Peticion {
    $inicio = Get-Date
    Invoke-RestMethod -Uri "$baseUrl/api/libros" -Method Get -WebSession $session | Out-Null
    $fin = Get-Date
    return ($fin - $inicio).TotalMilliseconds
}

function Vaciar-Cache {
    docker exec pfc_redis redis-cli FLUSHDB | Out-Null
}

# --- SIN CACHE: evict antes de cada llamada ---
Write-Host "=== SIN CACHE ==="
$tiemposSinCache = @()
for ($i = 1; $i -le 10; $i++) {
    Vaciar-Cache
    $t = Medir-Peticion
    $tiemposSinCache += $t
    Write-Host "Repeticion $i (sin cache): $([math]::Round($t,2)) ms"
}

# --- CON CACHE: un solo evict al inicio, luego repetimos dentro del TTL ---
Write-Host "`n=== CON CACHE ==="
Vaciar-Cache
Medir-Peticion | Out-Null   # llamada de "calentamiento" que llena el cache, no se mide
$tiemposConCache = @()
for ($i = 1; $i -le 10; $i++) {
    $t = Medir-Peticion
    $tiemposConCache += $t
    Write-Host "Repeticion $i (con cache): $([math]::Round($t,2)) ms"
}

$promedioSinCache = ($tiemposSinCache | Measure-Object -Average).Average
$promedioConCache = ($tiemposConCache | Measure-Object -Average).Average
$speedup = $promedioSinCache / $promedioConCache

Write-Host "`n=== RESULTADOS ==="
Write-Host "Promedio SIN cache: $([math]::Round($promedioSinCache,2)) ms"
Write-Host "Promedio CON cache: $([math]::Round($promedioConCache,2)) ms"
Write-Host "Speedup S = Tsin/Tcon: $([math]::Round($speedup,2))"